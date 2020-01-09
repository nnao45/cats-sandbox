package io.list_in_io_fold

object Main extends App {
  import cats.effect.IO

  def ioa(msg: String) = IO { println(s"hey, $msg!") }
  def iob(msg: String) = IO { println(s"hoi, $msg!") }
  def ioc(msg: String) = IO { println(s"hai, $msg!") }
  val list = Seq("one", "two", "three")

  val program1: IO[Unit] =
    list.foldLeft(IO.unit) { (a, c) => for {
        _  <- a
        _ <- ioa(c)
        _ <- iob(c)
        _ <- ioc(c)
      } yield ()
    }

  program1.unsafeRunSync()
}
