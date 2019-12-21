package io.throw01

object Main extends App {
  import cats.effect.IO

  val ioa = IO { println("hey!") }
  val except = IO.raiseError(new Exception)

  val program: IO[Unit] =
    for {
      _ <- ioa
      _ <- except
      _ <- ioa
    } yield ()

  program.unsafeRunSync()
}
