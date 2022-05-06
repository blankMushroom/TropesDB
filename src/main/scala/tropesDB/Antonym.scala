package tropesDB
import scalikejdbc._
object Antonym {
  def addAntonym(trope1Id:Int, trope2Id:Int) :Unit = {
    implicit val session = AutoSession
    sql"""insert into Antonyms(trope1Id, trope2Id) VALUES($trope1Id, $trope2Id)""".update().apply
  }

  def getAllantonymstotrope(tropeId:Int):Seq[Trope] = {
    implicit val session = AutoSession
    sql"""select * from Tropes
         INNER JOIN Antonyms ON Tropes.id = Synonyms.trope2Id
         WHERE trope1Id = $tropeId
         UNION
         INNER JOIN Antonyms ON Tropes.id = Synonyms.trope1Id
         WHERE trope2Id = $tropeId
       """.map(rs =>
      Trope(rs.int("Tropes.id"),
        rs.string("Tropes.name"),
        rs.string("Tropes.desc"))).list().apply()
  }
}
