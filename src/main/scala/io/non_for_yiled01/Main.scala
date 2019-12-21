package io.non_for_yiled01

object Main extends App {
  import cats.effect.IO

  def say(msg: String) = IO( println(s"hey, $msg") )

  IO.pure("ichiro")
    .flatMap(s =>  say(s))
    .flatMap(_ => IO.pure("tom"))
    .flatMap(s => say(s))
    .flatMap(_ => IO.unit)
    .unsafeRunSync()
}