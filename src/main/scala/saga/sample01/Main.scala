package saga.sample01

import cats.effect.{Concurrent, ContextShift, IO, Timer }
import com.vladkopanev.cats.saga.Saga._
import cats.syntax.all._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext


object Main extends App {
  implicit val ec: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
  var pay = 0d
  var point = 0d
  var order = 0
  val err = new Exception("error")

  def reset: IO[Unit] = IO{
    pay = 0d
    point = 0d
    order = 0
  } *> IO.unit
  def collectPayments(v1: Double, count: Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(pay += v1 * count)
  def refundPayments: IO[Unit] = IO{ pay = 0d } *> IO.unit
  def assignLoyaltyPoints(v1: Double, count: Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(point += v1 * count)
  def cancelLoyaltyPoints: IO[Unit] = IO{ point = 0d } *> IO.unit
  def openOrder(v1 : Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(order += v1)
  def reopenOrder(v1 : Int): IO[Unit] = IO(order += v1)
  def closeOrder(isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO{ order = 0 } *> IO.unit

  def orderSaga1(): IO[Unit] =
    (for {
      _ <- reset.noCompensate
      _ <- openOrder(1, false) compensate closeOrder(false)
      _ <- collectPayments(2d, 2, false) compensate refundPayments
      _ <- assignLoyaltyPoints(1d, 1, false) compensate  cancelLoyaltyPoints
      _ <- closeOrder(false) compensate reopenOrder(1)
    } yield ()).transact

  def orderSaga2(): IO[Unit] =
    (for {
      _ <- reset.noCompensate
      _ <- openOrder(1, false) compensate closeOrder(false)
      _ <- collectPayments(2d, 2, true) compensate refundPayments
      _ <- assignLoyaltyPoints(1d, 1, false) compensate  cancelLoyaltyPoints
      _ <- closeOrder(false) compensate reopenOrder(1)
    } yield ()).transact

  def orderSaga3(): IO[Unit] =
    (for {
      _ <- reset.noCompensate
      _ <- openOrder(1, false) compensate closeOrder(false)
      _ <- collectPayments(2d, 2, false) compensate refundPayments
      _ <- assignLoyaltyPoints(1d, 1, true) compensate  cancelLoyaltyPoints
      _ <- closeOrder(false) compensate reopenOrder(1)
    } yield ()).transact

  orderSaga1()
    .handleErrorWith(_ => IO.unit)
    .unsafeRunSync()

  assert(pay == 4d)
  assert(point == 1d)
  assert(order == 0)

  orderSaga2()
    .handleErrorWith(_ => IO.unit)
    .unsafeRunSync()

  assert(pay == 0d)
  assert(point == 0d)
  assert(order == 0)

  orderSaga3()
    .handleErrorWith(_ => IO.unit)
    .unsafeRunSync()

  assert(pay == 0d)
  assert(point == 0d)
  assert(order == 0)
}
