package io.list_in_io_fold

object Main extends App {
  import cats.effect.IO

  def ioa(msg: String) = IO { println(s"hey, $msg!") }
  val list = List("one", "two", "three")

  val program1: IO[Unit] =
    list.foldLeft(IO.unit) { (a, c) => for {
        _ <- a
        _ <- ioa(c)
      } yield ()
    }

  program1.unsafeRunSync()
}
