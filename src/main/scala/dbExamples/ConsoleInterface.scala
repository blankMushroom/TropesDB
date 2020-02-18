package dbExamples

object ConsoleInterface {
  def run(): Unit = {
    var end = false
    while (!end) {
      println("WLCM to HOOMAN AND DOGE DB ")
      println("1) ADD NEW HOOMAN")
      println("2) ADD NEW DOGE")
      println("3) ADD RELATION")
      println("4) SHOW ALL PAIRS")
      scala.io.StdIn.readInt() match {
        case 1 =>
          println("PRint name ")
          Hooman.addHooman(scala.io.StdIn.readLine())
        case 2 =>
          println("PRint name ")
          Doge.addDoge(scala.io.StdIn.readLine())
        case 3 =>
          println("print names")
          val hoomanName = scala.io.StdIn.readLine()
          val dogeName = scala.io.StdIn.readLine()
          val hoomanId = Hooman.getId(hoomanName)
          val dogeId = Doge.getId(dogeName)
          hoomanId.foreach { hi =>
            dogeId.foreach { di =>
              Relations.addRelation(hi, di)
            }
          }

        case 4 =>
          Relations.getAllHoomanDogePairs().foreach(println)

      }
    }
  }
}
