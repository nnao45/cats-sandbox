package io.io_in_io

object Main extends App {
  import cats.effect.IO

  val ioa = IO { println("hey!") }

  val ioioa = IO { IO { println("hey!") } }

  val program1: IO[Unit] =
    for {
      _ <- ioa
      _ <- ioioa
      _ <- ioa
    } yield ()

  program1.unsafeRunSync()

  val program2: IO[Unit] =
    ioa
      .flatMap(_ => ioioa)
      .flatMap(_ => ioa)
      .flatMap(_ => IO.pure())

  program2.unsafeRunSync()
}
