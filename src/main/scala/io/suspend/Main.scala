package io.suspend

import cats.effect.IO
import cats.effect._
import cats.implicits._

import scala.concurrent.ExecutionContext

object Main extends App {
  def fib(n: Int, a: Long = 0, b: Long = 1): IO[Long] =
    IO(a + b).flatMap { b2 =>
      if (n > 0)
        fib(n - 1, b, b2)
      else
        IO.pure(a)
    }

  def fibWithSuspend(n: Int, a: Long, b: Long)(implicit cs: ContextShift[IO]): IO[Long] =
    IO.suspend {
      if (n == 0) IO.pure(a) else {
        val next = fibWithSuspend(n - 1, b, a + b)
        // Every 100 cycles, introduce a logical thread fork
        if (n % 100 == 0)
          cs.shift *> next
        else
          next
      }
    }

  implicit val cs: ContextShift[IO] =
    IO.contextShift(ExecutionContext.global)

  println(fib(1000, 1000, 2).unsafeRunSync())
  println(fibWithSuspend(1000, 1000, 2).unsafeRunSync())
}
