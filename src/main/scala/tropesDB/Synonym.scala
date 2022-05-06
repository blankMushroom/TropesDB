package tropesDB
import scalikejdbc._
object Synonym {
  def addSynonym(trope1Id:Int, trope2Id:Int) :Unit = {
    implicit val session = AutoSession
    sql"""insert into Synonyms(trope1Id, trope2Id) VALUES($trope1Id, $trope2Id)""".update().apply
  }

  def getAllsynonymstotrope(tropeId:Int):Seq[Trope] = {
    implicit val session = AutoSession
    sql"""select * from Tropes
         INNER JOIN Synonyms ON Tropes.id = Synonyms.trope2Id
         WHERE trope1Id = $tropeId
         UNION
         INNER JOIN Synonyms ON Tropes.id = Synonyms.trope1Id
         WHERE trope2Id = $tropeId
       """.map(rs =>
      Trope(rs.int("Tropes.id"),
        rs.string("Tropes.name"),
        rs.string("Tropes.desc"))).list().apply()
  }
}
