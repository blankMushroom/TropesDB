package tropesDB



object ConsoleInterface {
  def run(): Unit = {
    var end = false
    while (!end) {
      println("Welcome to the TropeDB console UI")
      println("input \"n\" to Add new trope")
      println("input \"r\" to Define a relation between two tropes")
      println("input \"d\" to Retrieve a trope description")
      println("input \"s\" to Retrieve all tropes with a similar meaning to a given one")
      println("input \"g\" to Retrieve all tropes with the opposite meaning to a given one")

      scala.io.StdIn.readInt() match {
        case 'n' =>
          println("Input a name for the new Trope. Then press \"Enter\"")
          val name =  scala.io.StdIn.readLine()
          println("Now, input a description for your trope and press \"Enter\"")
          Trope.addTrope(name,scala.io.StdIn.readLine())
        case 'r' =>
          println("input \"s\" if you wish to specify that 2 tropes are synonyms, or input \"a\" if you wish to specify that 2 tropes are antonyms")
          scala.io.StdIn.readInt() match {
            case 's' =>
              println("input the name of the first trope, press \"enter\", then input the name of the second trope and press \"enter\" again")
              val t1Name = scala.io.StdIn.readLine()
              val t2Name = scala.io.StdIn.readLine()
              for (
                t1Id <- Trope.getId(t1Name);
                t2Id <- Trope.getId(t2Name)) {
                Synonym.addSynonym(t1Id, t2Id)
              }
            case 'a' =>
              println("input the name of the first trope, press \"enter\", then input the name of the second trope and press \"enter\" again")
              val t1Name = scala.io.StdIn.readLine()
              val t2Name = scala.io.StdIn.readLine()
              for (
                t1Id <- Trope.getId(t1Name);
                t2Id <- Trope.getId(t2Name)) {
                Antonym.addAntonym(t1Id, t2Id)
              }
          }
        case 'd' =>
          println("input the name of the trope, then press \"enter\"")
          println(Trope.getDesc(scala.io.StdIn.readLine()))
        case 's' =>
          println("input the name of the trope, then press \"enter\"")
          val tId = Trope.getId(scala.io.StdIn.readLine())
          if(!tId.isEmpty) {
            Synonym.getAllsynonymstotrope(tId.get).foreach(println)
          }
        case 'g' =>
          println("input the name of the trope, then press \"enter\"")
          val tId = Trope.getId(scala.io.StdIn.readLine())
          if(!tId.isEmpty) {
            Antonym.getAllantonymstotrope(tId.get).foreach(println)
          }
      }
    }
  }
}
