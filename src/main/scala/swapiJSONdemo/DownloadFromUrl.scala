package swapiJSONdemo

import scalaj.http.{Http, HttpResponse}

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object DownloadFromUrl {
  def download[A](url: String)(implicit dec:io.circe.Decoder[A]): Option[A] = {
    val response: HttpResponse[String] = Http(url).param("format", "json").asString
    decode[A](response.body).toOption
  }
}
