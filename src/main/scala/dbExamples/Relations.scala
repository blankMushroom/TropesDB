package dbExamples

import scalikejdbc._

object Relations {
  def addRelation(hoomanId:Int, dogeId:Int) :Unit = {
    implicit val session = AutoSession
    sql"""insert into Relations(hoomanId, dogeId) VALUES($hoomanId, $dogeId)""".update().apply
  }

  def getAllHoomanDogePairs():Seq[(Hooman, Doge)] = {
    implicit val session = AutoSession
    sql"""select * from Hoomans
         INNER JOIN Relations ON Hoomans.id = Relations.hoomanId
        INNER JOIN Doges ON Doges.id = Relations.DogeId
       """.map(rs =>
      (Hooman(rs.int("Hoomans.id"), rs.string("Hoomans.name")),
      Doge(rs.int("Doges.id"), rs.string("Doges.name")))).list().apply()
 //   INNER JOIN Doges ON Doges.id == Relations.DogeId
  }
}
