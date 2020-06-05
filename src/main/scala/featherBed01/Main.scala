package featherBed01

import java.net.URL

import com.twitter.util.Await
import featherbed.circe._
import featherbed.support.ContentType
// import featherbed.circe._

import io.circe.generic.auto._
// import io.circe.generic.auto._


case class Foo(someText: String, someVal: String)
case class Bar(someText: String, someVal: String)
// defined class Foo

object Main extends App {
  val client = new featherbed.Client(new URL("http://localhost:8767/api/"))
  Await.result {
    val request = client.post("foo/good")
      .withParams("foo" -> "bar")
      .accept("application/json")

    request.send[Foo]()
  }
}
