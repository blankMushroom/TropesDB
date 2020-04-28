package AsyncSocketsGame

import java.io.PrintWriter
import java.net.{ServerSocket, Socket}
import java.util.Scanner

import sockets.Server.{player1, srv}

object Server extends GameData {
  var x1: Int = 300
  var y1: Int = 300

  var x2: Int = 300
  var y2: Int = 400


  var sx1: Int = 0
  var sx2: Int = 0


  def tick(): Unit = {

    x1 += sx1
    x2 += sx2


  }

  def main(args: Array[String]): Unit = {
    val srv: ServerSocket = new ServerSocket(1337)
    val p1 = srv.accept()
    val p2 = srv.accept()
    val toP1 = new PrintWriter(p1.getOutputStream, true)
    val fromP1 = new Scanner(p1.getInputStream)
    val toP2 = new PrintWriter(p2.getOutputStream, true)
    val fromP2 = new Scanner(p2.getInputStream)

    new Thread(() => {
      while (true) {
        fromP1.nextLine() match {
          case "LEFT" => sx1 = -1
          case "RIGHT" => sx1 = 1
        }
      }
    }).start()

    new Thread(() => {
      while (true) {
        fromP2.nextLine() match {
          case "LEFT" => sx2 = -1
          case "RIGHT" => sx2 = 1
        }
      }
    }).start()


    //GAME LOOP
    while (true) {
      tick()
      //send world state to clients
      toP1.println(s"world $x1 $y1 $x2 $y2")
      toP2.println(s"world $x1 $y1 $x2 $y2")
      Thread.sleep(1000 / 60)
    }

  }
}
