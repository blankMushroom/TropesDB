package jsoupExamples

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}

import scala.jdk.CollectionConverters._


object JSoupTest extends App {
  val doc: Document = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_conspiracy_theories").get()
  //  println(doc)
  val content: Element = doc
    .getElementById("mw-content-text")
    .getElementsByClass("mw-parser-output")
    .get(0)
  //"Economics_and_society")
  //   println(content)
  val candidates = content.children().asScala.toSeq

  val selected = candidates
    .dropWhile(el => el.tagName() != "h2" ||
      (el.children().size() == 0 ||
        el.children().get(0).id() != "Economics_and_society"))
    .drop(1).takeWhile(el => el.tagName() != "h2")
  def splitAtH3(s:Seq[Element]):Seq[(Element, Seq[Element])] = if(s.isEmpty) Seq() else{
    val h3 = s.head
    val toNexth3 = s.drop(1).takeWhile(_.tagName() != "h3")
    val left = s.drop(1 + toNexth3.length)
    (h3, toNexth3) +: splitAtH3(left)
  }
  val splited = splitAtH3(selected)
 // println(splited)

  splited.foreach{(s) =>
    println(f"${s._1.text()}%20s | ${s._2.map(_.text).reduce(_ + _ )}")
  }
  //  contentText.getElementsByTag("a").asScala.map(a => a.attr("href")).foreach(println)


}
