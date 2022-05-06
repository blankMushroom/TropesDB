package tropesDB

import scalikejdbc._

object DBmain {

  def main(args: Array[String]): Unit = {
    initDb()
    ConsoleInterface.run()
  }

  def initDb(): Unit ={
    // initialize JDBC driver & connection pool
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:file:./data/tropedb:db", "user", "pass")
    //    ConnectionPool.singleton("jdbc:h2:mem:db", "user", "pass")

    initTables()


    //    Hooman.addHooman("Andrey")
    //    Hooman.addHooman("Adolf Alexandrovich")
    //    Doge.addDoge("Sharik")
    //    Doge.addDoge("Woofer")
    //    Doge.addDoge("Awawawaw")
    //    Relations.addRelation(1, 2)
    //    Hooman.reqHoomans().foreach(println)
    //
    //
    //    Relations.getAllHoomanDogePairs().foreach(println)
    //    println(Hooman.hasDoge("Andrey"))
  }


  def initTables():Unit = {
    implicit val session = AutoSession
    sql"""create table if not exists Tropes(
          id serial not null primary key,
          name  varchar(64)
          desc varchar(400)
          )
       """.execute().apply()

    sql"""
         create table if not exists Synonyms(
         id serial not null primary key,
         trope1Id int,
         trope2Id int
         )
       """.execute().apply()

    sql"""
         create  table if not exists Antonyms(
         id serial not null primary key,
         trope1Id int,
         trope2Id int
         )
       """.execute().apply()
  }


}

