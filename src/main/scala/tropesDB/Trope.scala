package tropesDB
import scalikejdbc._
object Trope {
  def addTrope(name:String, desc:String) :Unit = {
    implicit val session = AutoSession
    sql"""insert into Tropes(name) VALUES($name,$desc)""".update().apply
  }

  def getId(name:String):Option[Int] = {
    implicit val session = AutoSession
    sql"""
         SELECT *
         FROM Tropes
         WHERE name = $name
         """.map(rs => rs.int("id")).single().apply()
  }
  def getDesc(name:String):Option[String] = {
    implicit val session = AutoSession
    sql"""
         SELECT *
         FROM Tropes
         WHERE name = $name
         """.map(rs => rs.string("desc")).single().apply()
  }
}
case class Trope(id:Int, name:String, desc:String)