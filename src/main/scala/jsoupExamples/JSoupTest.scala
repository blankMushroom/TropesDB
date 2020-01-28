package jsoupExamples

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object JSoupTest extends App{
  val doc:Document = Jsoup.connect("https://en.wikipedia.org/wiki/Elementary_matrix").get()
  println(doc)


}
