package io.list_in_io

object Main extends App {
  import cats.effect.IO

  def ioa(msg: String) = IO { println(s"hey, $msg!") }
  val list = List("one", "two", "three")

  val program1: IO[Unit] =
    IO(list.foreach(c => for {
      _ <- ioa(c)
    } yield ()))

  program1.unsafeRunSync()
}
