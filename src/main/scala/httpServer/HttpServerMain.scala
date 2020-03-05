package httpServer

import spark.{Request, Response, Route, Spark}

import scala.util.Random
import scalatags.Text.all._

object HttpServerMain extends App {

  case class Human(name: String, id: Int)

  val w: Seq[String] = Seq("am", "la", "da", "de", "do", "lu")
  var humans: Seq[Human] = for (i <- 1 to 10) yield Human(w(new Random().nextInt(w.size)) + w(new Random().nextInt(w.size)) + w(new Random().nextInt(w.size)), i)

  //  Spark.staticFileLocation("/serverData/")
  Spark.externalStaticFileLocation("/home/azu/Documents/scala-newtworking-test/serverData")
  Spark.port(8080)
  Spark.get("/", (request: Request, response: Response) => PageTemplate.genPage("<h1>MAIN PAGE</h1>"))
  Spark.get("/add", (request: Request, response: Response) => PageTemplate.genPage(
    form(method := "post", action :="/addNew")(
      label(`for` := "name")("name"),
      input(`type` := "text", id:="name", name := "name"),
      br(),
      label(`for` := "index")("index"),
      input(`type` := "text", id:="index", name := "index"),
      input(`type` := "submit")
    ).render
  ))
  Spark.post("/addNew", (request: Request, response: Response) => {
    val name= request.queryParams("name")
    val id= request.queryParams("index")
    id.toIntOption match {
      case Some(value) => if(name.length > 0){
        humans = Human(name, value) +: humans
      }
      case None =>
    }
    response.redirect("/list")
    ""
  })

  Spark.get("/list", (request: Request, response: Response) => {
    PageTemplate.genPage(
      div(table(border := "1px")(
        tr(
          th("id"),
          th("name")),
        humans.map(h => tr(
          td(h.id.toString),
          td(h.name.toString)
        ))
      )
      ).render
    )
  })

}
