package httpServer

import spark.{Request, Response, Route, Spark}

import scala.util.Random
import scalatags.Text.all._

object HttpServerMain extends App {

  case class Human(name: String, id: Int)

  val w: Seq[String] = Seq("am", "la", "da", "de", "do", "lu")
  var humans: Seq[Human] = for (i <- 1 to 10) yield Human(w(new Random().nextInt(w.size)) + w(new Random().nextInt(w.size)) + w(new Random().nextInt(w.size)), i)

  //  Spark.staticFileLocation("/serverData/")
  //  Spark.ipAddress("192.168.3.255")
  Spark.port(8080)
  Spark.externalStaticFileLocation("/home/azu/Documents/scala-newtworking-test/serverData")
  Spark.get("/", (request: Request, response: Response) => PageTemplate.genPage("<h1>MAIN PAGE</h1>"))
  Spark.get("/add", (request: Request, response: Response) => PageTemplate.genPage(
    form(method := "post", action := "/addNew")(
      label(`for` := "name")("name"),
      input(`type` := "text", id := "name", name := "name"),
      br(),
      label(`for` := "index")("index"),
      input(`type` := "text", id := "index", name := "index"),
      input(`type` := "submit")
    ).render
  ))
  Spark.post("/addNew", (request: Request, response: Response) => {
    val name = request.queryParams("name")
    val id = request.queryParams("index")
    id.toIntOption match {
      case Some(value) => if (name.length > 0) {
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

  val findId = "findId"
  val resultDivID = "resultDivId"
  val findReqPath = "findReq"
  val host = "http://localhost:8080"

  val findJs =
    s"""
      |var input = document.getElementById("$findId");
      |var output = document.getElementById("$resultDivID")
      |
      |input.addEventListener("input", (event) => {
      |    var xhr = new XMLHttpRequest();
      |    xhr.open("GET", "$host" + "/$findReqPath?q=" + input.value  )
      |    xhr.onload = function() {
      |        if (xhr.status == 200) {
      |            console.log("response :" + xhr.responseText)
      |            var resArr = JSON.parse(xhr.responseText)
      |            var html = ""
      |            for (var i = 0; i < resArr.length; i++) {
      |                html += "<li>" + resArr[i].id + " " + resArr[i].name + "</li>"
      |            }
      |            html = "<ul>" + html + "</ul>"
      |            output.innerHTML = html
      |        }
      |   }
      |   console.log("requesting :" + input.value)
      |   xhr.send()
      |});
      |""".stripMargin


  Spark.get("/find", (request: Request, response: Response) => PageTemplate.genPage(
    div(
      p("Lets find someone"),
      input(
        `type` := "text",
        placeholder := "Type here!",
        id := findId
      ),
      div(
        minHeight := "300px",
        id := resultDivID,
      )(),
      script( raw(
        findJs
        )
      )
    ).render
  ))
  Spark.get(s"/$findReqPath", (request: Request, response: Response) =>{
    val q = request.queryParams("q")
    print(s"Requested info about: $q")
    val result = humans.filter(h => h.name.contains(q))
    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
    result.asJson
  }
  )
}
