package dbExamples
import scalikejdbc._

object Doge {
  def addDoge(name:String) :Unit = {
    implicit val session = AutoSession
    sql"""insert into Doges(name) VALUES($name)""".update().apply
  }

  def getId(name:String):Option[Int] = {
    implicit val session = AutoSession
    sql"""
         SELECT *
         FROM Doges
         WHERE name = $name
         """.map(rs => rs.int("id")).single().apply()
  }

}

case class Doge(id:Int, name:String)