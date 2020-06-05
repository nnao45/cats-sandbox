package io.fiber

object Main extends App {
  import cats.effect.{Fiber, IO}
  import cats.implicits._

  import scala.concurrent.ExecutionContext.Implicits.global
  // Needed for `start`
  implicit val ctx = IO.contextShift(global)

  val io = IO(println("Hello!"))

  val ioNever = IO.never *> IO(println("Hello!"))

//  val runToBunker = IO.raiseError(new Exception( "To the bunker!!!"))

  val task = for {
    starttedNever <- ioNever.start
    strtted1 <- io.start
    strtted2 <- io.start
    _ <- starttedNever.join
    _ <- strtted1.join
//    _ <- strtted2.join
  } yield ()

  task.unsafeRunSync()
}
