import scalaj.http._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object HttpJsonRequestAndParse extends App {



  val response: HttpResponse[String] = Http("https://swapi.co/api/people/1/").param("format", "json").asString
  println(s"response\n${response.body}")

  case class People(
                     name: String,
                     height: Int,
                     mass: Int,
                     hair_color: String,
                     homeworld: String,
                     starships: Seq[String]
                   )

  //decode
  val decodedPeople = decode[People](response.body)
  println(decodedPeople)
  val people = decodedPeople.right.get//getOrElse(People("", 1, 1, "", "", Seq()))
  println(people)
  //encode
  println(s" encoded to json \n ${people.asJson}")

  case class Ship(
                 name:String,
                 model:String,
                 manufacturer: String,
                 cost_in_credits:Int,
                 length:Double,
                 max_atmosphering_speed:Double,
                 pilots:Seq[String]
                 )
  println("requesting ships info")
  val decodedShips = people.starships.map { ship =>
    val shipResponse: HttpResponse[String] = Http(ship).param("format", "json").asString
    println(shipResponse.body)
    decode[Ship](shipResponse.body).right.get
//    shipResponse
  }
  decodedShips.foreach(println)


}