package AsyncSocketsGame

import java.io.PrintWriter
import java.net.{ServerSocket, Socket}
import java.util.Scanner

import sockets.Server.{player1, srv}

object AsyncServer {
  var gameRules: GameState = _

  object idProvider {
    private[this] var curId: Long = 0
    def getId():Long = {
        curId += 1
        curId
    }
  }

  def main(args: Array[String]): Unit = {
    val srv: ServerSocket = new ServerSocket(1337)
    val p1 = srv.accept()
    val p2 = srv.accept()
    val toP1 = new PrintWriter(p1.getOutputStream, true)
    val fromP1 = new Scanner(p1.getInputStream)
    val toP2 = new PrintWriter(p2.getOutputStream, true)
    val fromP2 = new Scanner(p2.getInputStream)

    gameRules = new GameState(
      Player(idProvider.getId(), "pl1", V2(100, 100)),
      Player(idProvider.getId(), "pl2", V2(200, 100)),
    )

    new Thread(() => {
      while (true) {
        fromP1.nextLine() match {
          case "LEFT" => gameRules.p1Dir = V2(-1, 0)
          case "RIGHT" => gameRules.p1Dir = V2(1, 0)
          case "SHOOT" => gameRules.shoot(gameRules.p1)
        }
      }
    }).start()

    new Thread(() => {
      while (true) {
        fromP2.nextLine() match {
          case "LEFT" => gameRules.p2Dir = V2(-1, 0)
          case "RIGHT" => gameRules.p2Dir = V2(1, 0)
          case "SHOOT" => gameRules.shoot(gameRules.p2)
        }
      }
    }).start()


    //GAME LOOP
    while (true) {
      gameRules.tick()
      //send world state to clients
      import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
      val sateJson:String = gameRules.snapshot.asJson.noSpaces
      toP1.println(sateJson)
      toP2.println(sateJson)
      Thread.sleep(1000 / 60)
    }

  }
}
