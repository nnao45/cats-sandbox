package apply.sample01

import cats.effect.IO
import cats.syntax.all._

object Main extends App {
  var counter = 0
  val task = IO{ counter += 1 } *> IO{ counter += 1 } *> IO{ counter += 1 } *> IO.unit
  task.unsafeRunSync()

  assert( counter == 3)
}
