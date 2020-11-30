package io.if_else

import cats.effect.IO
import cats.syntax.all._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Main extends App {

  val func1 = IO(println("hey!!")) *> IO {
    1 + 2
  }

  val func2 = IO(println("heyhey!!")) *> IO {
    2 + 3
  }

  val func3 = IO(func1.map(a => func2.map(_ + a)))

//  val trigger = true
  val trigger = false

  println(if (trigger) 0 else func3.unsafeRunSync().unsafeRunSync().unsafeRunSync())

  implicit val cx = ExecutionContext.global

  val func4 = for {
    _ <- Future(println("poi!!"))
    ret <- Future {
            1 + 2
          }
  } yield ret

  val func5 = for {
    _ <- Future(println("poipoi!!"))
    ret <- Future {
            2 + 3
          }
  } yield ret

  val func6 = Future(func4.map(a => func5.map(_ + a)))

  println(if (trigger) 0 else Await.result(func6, Duration.Inf))
}
