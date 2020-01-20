package swapiJSONdemo


import io.circe.parser.decode
import scalaj.http.{Http, HttpResponse}
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object Ship {

  def downloadFromUrl(url: String): Option[Ship] = {
    try {
      val response: HttpResponse[String] = Http(url)
        .param("format", "json")
        .timeout(5000, 5000)
        .asString
      decode[Ship](response.body).toOption
    } catch {
      case e: Throwable => println(e)
        None
    }
  }
}



case class Ship(
                 name: String,
                 model: String,
                 manufacturer: String,
                 cost_in_credits: Int,
                 length: Double,
                 max_atmosphering_speed: Double,
                 pilots: Seq[String]
               )