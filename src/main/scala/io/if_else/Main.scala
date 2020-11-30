package io.if_else

import cats.effect.IO
import cats.syntax.all._

object Main extends App {

  val func1 = IO(println("hey!!")) *> IO {
    1 + 2
  }

  val func2 = IO(println("heyhey!!")) *> IO {
    2 + 3
  }

  val func3 = IO(func1.map(a => func2.map(_ + a)))

  val trigger = true
//  val trigger = false

  println(if (trigger) 0 else func3.unsafeRunSync().unsafeRunSync().unsafeRunSync())
}
