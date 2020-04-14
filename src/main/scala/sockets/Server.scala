package sockets

import java.io.PrintWriter
import java.net.{ServerSocket, Socket}
import java.util.Scanner

object Server extends App{
  val srv:ServerSocket = new ServerSocket(1337)
  println("server started")

  val player1: Socket  = srv.accept();//wait for connection ...
  val toP1 = new PrintWriter(player1.getOutputStream, true)
  val fromP1 = new Scanner(player1.getInputStream)
  toP1.println("Player 1 welcome to game, enter your name:")//greet

  val player2: Socket  = srv.accept();//wait for connection ...
  val toP2 = new PrintWriter(player2.getOutputStream, true)
  val fromP2 = new Scanner(player2.getInputStream)
  toP2.println("Player 2 welcome to game, enter your name:")//greet

  val p1Name = fromP1.nextLine()//receive name
  val p2Name = fromP2.nextLine()//receive name

  println(s"player 1 name: $p1Name")
  println(s"player 2 name: $p2Name")

  toP1.println(s"$p1Name enter your fav. anime?")//anime
  toP2.println(s"$p2Name enter your fav. anime?")//anime

  val p1Anime = fromP1.nextLine()//receive anime
  val p2Anime = fromP2.nextLine()//receive anime

  toP1.println(s"$p2Name fav. anime is $p2Anime")//others anime
  toP2.println(s"$p1Name fav. anime is $p1Anime")//others anime

}
