package io.pure01

object Main extends App {
  import cats.effect.IO

  def say(msg: String) = IO( println(s"hey, $msg") )

  val program: IO[Unit] = for {
    ichiro <- IO.pure("ichiro")
    tom <- IO.pure("tom")
    _ <- say(ichiro)
    _ <- say(tom)
  } yield ()

  program.unsafeRunSync()
}
