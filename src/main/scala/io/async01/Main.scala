package io.async01


import cats.effect.{Async, ContextShift, IO}

import scala.concurrent
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Random, Success}

object Main extends App {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
  implicit val ex: ExecutionContext = ExecutionContext.global
  val msg = "hello"
  val random = Random
  val f1: Future[Unit] = Future {
    (1 to 100).foreach( i => {
      Thread.sleep(random.nextLong())
      println(i)
    })
  }
  val f2: Future[Unit] = Future {
    (1 to 100).foreach( i => {
      Thread.sleep(random.nextLong())
      println(i)
    })
  }
  val f3: Future[Unit] = Future {
    (1 to 100).foreach( i => {
      Thread.sleep(random.nextLong())
      println(i)
    })
  }

  val fs = Seq.empty[Future[Unit]] :+ f1 :+ f2 :+ f3
  fs.foreach(f => {
    f.onComplete {
      case Success(_) => println(s"complete!: ${Thread.currentThread().getName}")
      case Failure(t) => println(t.getMessage())
    }
    Await.ready(f, Duration.Inf)
  })
//  val f2: Future[Unit] = Future {
//    (1 to 100).foreach( i => {
//      Thread.sleep(random.nextLong(100))
//      println(i)
//    })
//  }
//  val f3: Future[Unit] = Future {
//    (1 to 100).foreach( i => {
//      Thread.sleep(random.nextLong(100))
//      println(i)
//    })
//  }
//  f1.onComplete {
//    case Success(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Failure(t)   => println(t.getMessage())
//  }
//  f2.onComplete {
//    case Success(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Failure(t)   => println(t.getMessage())
//  }
//  f3.onComplete {
//    case Success(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Failure(t)   => println(t.getMessage())
//  }

//  Await.ready(f1, Duration.Inf)
//  Await.ready(f2, Duration.Inf)
//  Await.ready(f3, Duration.Inf)
//
//  val ioa1: IO[Unit] =
//    Async[IO].async { cb =>
//      f1.onComplete {
//        case Success(value) => cb(Right(value))
//        case Failure(error) => cb(Left(error))
//      }
//    }
//
//  val ioa2: IO[Unit] =
//    Async[IO].async { cb =>
//      f2.onComplete {
//        case Success(value) => cb(Right(value))
//        case Failure(error) => cb(Left(error))
//      }
//    }
//
//  val ioa3: IO[Unit] =
//    Async[IO].async { cb =>
//      f3.onComplete {
//        case Success(value) => cb(Right(value))
//        case Failure(error) => cb(Left(error))
//      }
//    }
//
//  ioa1.unsafeRunAsync{
//    case Right(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Left(t)   => println(t.getMessage())
//  }
//  ioa2.unsafeRunAsync{
//    case Right(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Left(t)   => println(t.getMessage())
//  }
//  ioa3.unsafeRunAsync{
//    case Right(_) => println(s"complete!: ${Thread.currentThread().getName}")
//    case Left(t)   => println(t.getMessage())
//  }

//  Await.ready(f1, Duration.Inf)
//  Await.ready(f2, Duration.Inf)
//  Await.ready(f3, Duration.Inf)
}
