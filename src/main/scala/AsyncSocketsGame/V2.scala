package AsyncSocketsGame

case class V2(i: Int, j: Int) {
  def x:Int  = i

  def y:Int  = j

  def area:Int = i * j

  def opposite: V2 = V2(-i, -j)

  def unary_- : V2 = opposite

  def +(v: V2):V2 = V2(i + v.i, j + v.j)

  def -(v: V2): V2 = V2(i - v.i, j - v.j)

  def *(v: V2): V2 = V2(i * v.i, j * v.j)

  def /(v: V2): V2 = V2(i / v.i, j / v.j)

  def toV2: V2 = V2(i, j)

}
