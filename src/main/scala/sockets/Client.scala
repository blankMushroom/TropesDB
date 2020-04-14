package sockets

import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner


object Client extends App {
  val srv: Socket = new Socket("localhost", 1337)
  val toServer = new PrintWriter(srv.getOutputStream, true)
  val fromServer = new Scanner(srv.getInputStream)

  println(fromServer.nextLine())//greet
  val name = scala.io.StdIn.readLine() //
  toServer.println(name)//send name

  println(fromServer.nextLine())//anime
  val anime = scala.io.StdIn.readLine()
  toServer.println(anime)//send anime

  println(fromServer.nextLine())//others anime

//  if(fromServer.hasNextLine)fromServer.nextLine()





}
