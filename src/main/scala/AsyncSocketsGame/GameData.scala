package AsyncSocketsGame

sealed trait GameObject {
  def id: Long
}

case class Bullet(override val id: Long, pos: V2, dir: V2) extends GameObject {
  def move(): Bullet = copy(pos = pos + dir)
}

case class Player(override val id: Long, name: String, pos: V2) extends GameObject {
  def move(delta: V2): Player = copy(pos = pos + delta)
}

case class GameStateSnapshot(
                              p1: Player,
                              p2: Player,
                              bullets: Seq[Bullet] = Seq()
                            )

class GameState(

                 var p1: Player,
                 var p2: Player,
                 var p1Dir: V2 = V2(0, 0),
                 var p2Dir: V2 = V2(0, 0),
                 var bullets: Seq[Bullet] = Seq()

               ) {

  def snapshot: GameStateSnapshot = GameStateSnapshot(p1, p2, bullets)

  def shoot(p: Player): Unit = {
    bullets = bullets :+ Bullet(AsyncServer.idProvider.getId(), p.pos, V2(0, 1))
  }

  def tick(): Unit = {
    p1 = p1.move(p1Dir)
    p2 = p2.move(p2Dir)
    bullets = bullets.map(_.move())
  }

}

