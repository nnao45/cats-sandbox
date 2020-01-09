package io.handle_error

import cats.effect.IO

object Main extends App {
  var pay = 0d
  var point = 0d
  var order = 0
  val err = new Exception("error")

  def reset(): IO[Unit] = {
    pay = 0d
    point = 0d
    order = 0
    IO.unit
  }
  def collectPayments(v1: Double, count: Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(pay += v1 * count)
  def refundPayments: IO[Unit] = {
    pay = 0d
    IO.unit
  }
  def assignLoyaltyPoints(v1: Double, count: Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(point += v1 * count)
  def cancelLoyaltyPoints: IO[Unit] = {
    point = 0d
    IO.unit
  }
  def openOrder(v1 : Int, isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else IO(order += v1)
  def reopenOrder(v1 : Int): IO[Unit] = IO(order += v1)
  def closeOrder(isError: Boolean): IO[Unit] = if (isError) IO.raiseError(err) else {
    order = 0
    IO.unit
  }

  def orderSaga1(): IO[Unit] =
    for {
      _ <- reset()
      _ <- openOrder(1, false)
      _ <- collectPayments(2d, 2, false) handleErrorWith (_ => refundPayments)
      _ <- assignLoyaltyPoints(1d, 1, false) handleErrorWith (_ => cancelLoyaltyPoints)
      _ <- closeOrder(false) handleErrorWith (_ => reopenOrder(1))
    } yield ()

  def orderSaga2(): IO[Unit] =
    for {
      _ <- reset()
      _ <- openOrder(1, false)
      _ <- collectPayments(2d, 2, true) handleErrorWith (_ => refundPayments)
      _ <- assignLoyaltyPoints(1d, 1, false) handleErrorWith (_ => cancelLoyaltyPoints)
      _ <- closeOrder(false) handleErrorWith (_ => reopenOrder(1))
    } yield ()

  def orderSaga3(): IO[Unit] =
    for {
      _ <- reset()
      _ <- openOrder(1, false)
      _ <- collectPayments(2d, 2, false) handleErrorWith (_ => refundPayments)
      _ <- assignLoyaltyPoints(1d, 1, true) handleErrorWith (_ => cancelLoyaltyPoints)
      _ <- closeOrder(false) handleErrorWith (_ => reopenOrder(1))
    } yield ()

  orderSaga1().unsafeRunSync()

  assert(pay == 4d)
  assert(point == 1d)
  assert(order == 0)

  orderSaga2().unsafeRunSync()
  assert(pay == 0d)
  assert(point == 1.0d)
  assert(order == 0)

  orderSaga3().unsafeRunSync()
  assert(pay == 4d)
  assert(point == 0d)
  assert(order == 0)
}
