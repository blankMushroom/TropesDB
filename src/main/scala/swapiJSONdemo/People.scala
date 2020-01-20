package swapiJSONdemo

import scalaj.http.{Http, HttpResponse}
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._


object People{
  def getById(id:Int):Option[People] = downloadFromUrl(s"https://swapi.co/api/people/$id/")

  def downloadFromUrl(url:String ):Option[People] = {
    try {
      val response: HttpResponse[String] = Http(url)
        .param("format", "json")
        .timeout(5000, 5000)
        .asString
      decode[People](response.body).toOption
    } catch {
      case e: Throwable => println(e)
        None
    }
  }

}

case class People(
                   name: String,
                   height: Int,
                   mass: Int,
                   hair_color: String,
                   homeworld: String,
                   starships: Seq[String]
                 )
