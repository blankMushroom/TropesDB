package jsoupExamples

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import scala.jdk.CollectionConverters._
import javax.imageio.ImageIO

object JsoupTest2 extends App {
  val doc: Document = Jsoup.connect("http://www.jewish-museum.ru/events/").get()
  val content: Elements = doc.getElementsByClass("exposition-list-wrap").get(0).child(0).children()
  println(content.iterator().asScala.toSeq
    .map(_.getElementsByClass("price-ticket").get(0))
    .filter(_.getElementsByTag("i").size != 0)
    .map(_.getElementsByTag("i").get(0).text().dropRight(5).filter(_ != ' '))
    .flatMap(_.toIntOption).sum
  )


}
