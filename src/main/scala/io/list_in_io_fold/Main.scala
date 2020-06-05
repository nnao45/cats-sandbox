package io.list_in_io_fold

object Main extends App {
  import cats.effect.IO
  import cats.implicits._

  def ioa(msg: String) = IO { println(s"hey, $msg!") }
  def iob(msg: String) = IO { println(s"hoi, $msg!") }
  def ioc(msg: String) = IO { println(s"hai, $msg!") }
  val list = Seq("one", "two", "three")

//  val program1: IO[Unit] = {
//    var result = IO.unit
//    val op = (a: IO[Unit], c: String) => a.flatMap(_ => ioa(c)).flatMap(_ => iob(c)).map(_ => ioc(c))
//    list foreach (x => result = op(result, x))
//    result
//  }
  val program1: IO[Unit] =
    list.foldLeft(IO.unit) { (a, c) =>
      a.flatMap(_ => ioa(c)).flatMap(_ => iob(c)).map(_ => ioc(c))
    }

  val program2 =
    list.map(e => ioa(e)).toList.sequence

  //program1.unsafeRunSync()

  program2.unsafeRunSync()
}
