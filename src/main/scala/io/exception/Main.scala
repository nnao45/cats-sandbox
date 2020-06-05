package io.exception

import cats.effect.IO

object Main extends App {
  val ioa = IO { println("hey!") }
  val ioe = IO { throw new Exception("boom")}

  val program: IO[Unit] =
    for {
      _ <- ioa
      _ <- ioa
      _ <- ioe
      _ <- ioa
    } yield ()

  program.unsafeRunSync()
}
