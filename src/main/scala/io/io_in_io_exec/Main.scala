package io.io_in_io_exec

object Main extends App {
  import cats.effect.IO

  val ioa = IO { println("hey!") }
  val ioioa = IO { IO { println("hey!") } }

  val program1: IO[Unit] =
    for {
      _ <- ioa
      _ <- ioioa
        .flatMap(a => a)
      _ <- ioa
    } yield ()

  program1.unsafeRunSync()

  val program2: IO[Unit] =
    for {
      _ <- ioa
      ia <- for {
        ia <- ioioa
      } yield ia
      _ <- ia
      _ <- ioa
    } yield ()

  program2.unsafeRunSync()
}
