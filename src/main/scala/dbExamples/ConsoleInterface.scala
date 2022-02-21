package dbExamples

object ConsoleInterface {
  def run(): Unit = {
    var end = false
    while (!end) {
      println("WELCOME to HOOMAN AND DOGE DB ")
      println("1) ADD NEW HOOMAN")
      println("2) ADD NEW DOGE")
      println("3) ADD RELATION")
      println("4) SHOW ALL PAIRS")
      println("5) SHOW OWNED DOGES")
      scala.io.StdIn.readInt() match {
        case 1 =>
          println("Add HOOMAN ")
          Hooman.addHooman(scala.io.StdIn.readLine())
        case 2 =>
          println("Add DOGE ")
          Doge.addDoge(scala.io.StdIn.readLine())
        case 3 =>
          println("add relation")
          val hoomanName = scala.io.StdIn.readLine()
          val dogeName = scala.io.StdIn.readLine()
          for (
            hoomanId <- Hooman.getId(hoomanName);
            dogeId <- Doge.getId(dogeName)) {
            Relations.addRelation(hoomanId, dogeId)
          }
        case 4 =>
          Relations.getAllHoomanDogePairs().foreach(println)
        case 5 =>
          val hoomanName = scala.io.StdIn.readLine()
          for(h <- Hooman.reqHooman(hoomanName);
              d <- h.myDogs){
            println(d)
          }
      }
    }
  }
}
