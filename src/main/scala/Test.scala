import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object Test extends App{
  sealed trait Foo
  case class Bar(xs: Vector[String]) extends Foo
  case class Qux(i: Int, d: Double) extends Foo

  val foo: Foo = Qux(13, 14.0)

  val json = foo.asJson.noSpaces
  println(json)

  val decodedFoo = decode[Foo](json)
  println(decodedFoo)
}
