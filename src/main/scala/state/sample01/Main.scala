package state.sample01

case class V(value: Int)

object Main extends App {
  val result1: Either[V, Unit] = Right(Unit)
  val result2: Either[V, Unit] = Right(Unit)
  val result3: Either[V, Unit] = Left(V(1))
  val result4: Either[V, Unit] = Left(V(2))
  var counter = 0

  val ret1 = (for {
    _ <- result1
    _ <- result2
    _ <- result3
    _ <- result4
  } yield ()).left.get

  assert(ret1.value == 1)

  val result5: Option[V] = None
  val result6: Option[V] = None
  val result7: Option[V] = Some(V(2))
  val result8: Option[V] = None

  val ret2 = result5
              .orElse(result6)
              .orElse(result7)
              .orElse(result8)
              .get

  assert(ret2.value == 2)
}
