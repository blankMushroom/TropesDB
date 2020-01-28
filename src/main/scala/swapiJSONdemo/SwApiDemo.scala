package swapiJSONdemo

object SwApiDemo extends App{
  val p1 = People.getById(11)
  println(p1)
  p1 match {
    case Some(person) =>
      val ships = person.starships.flatMap(Ship.downloadFromUrl)
      println(ships)
      val pilots = ships.flatMap(s => s.pilots).toSet.flatMap(People.downloadFromUrl).map(_.name)
      println(pilots)
    case None =>
      println("Cant load person data")
  }
}
