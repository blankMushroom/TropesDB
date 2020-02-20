package dbExamples

import scalikejdbc._

object DbExampleMain {

  def main(args: Array[String]): Unit = {
    initDb()
    ConsoleInterface.run()
  }

  def initDb(): Unit ={
    // initialize JDBC driver & connection pool
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:file:./data/testdb:db", "user", "pass")
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
    sql"""create table if not exists Hoomans(
          id serial not null primary key,
          name  varchar(64)
          )
       """.execute().apply()

    sql"""
          create table if not exists Doges(
          id serial not null primary key,
          name varchar(64)
          )
       """.execute().apply()

    sql"""
         create  table if not exists Relations(
         id serial not null primary key,
         hoomanId int,
         dogeId int )
       """.execute().apply()
  }


}
