package io.free

import cats.effect.IO
import cats.free.Free
import cats.free.Free.liftF
import cats.~>
import cats.syntax.all._

sealed trait SampleOperation[A]

case class PrintMessage[T](msg: String) extends SampleOperation[T]

trait SampleOps {

  type SampleFree[A] = Free[SampleOperation, A]

  def printF[T](msg: String): SampleFree[T] =
    liftF[SampleOperation, T](PrintMessage[T](msg))
}

object Main extends App with SampleOps {

  val first: List ~> Option = new (List ~> Option) {
    override def apply[A](fa: List[A]): Option[A] = fa.headOption
  }

  def print(msg: String): IO[Unit] =
    printF(msg).foldMap(compile)

  private[this] def compile: SampleOperation ~> IO =
    new (SampleOperation ~> IO) {

      override def apply[A](fa: SampleOperation[A]): IO[A] = fa match {
        case PrintMessage(msg: String) =>
          IO {
            println(msg)
          } map {
            _.asInstanceOf[A]
          }
      }
    }

  print("hello").unsafeRunSync()
}
