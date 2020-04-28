package AsyncSocketsGame

import java.awt.{BasicStroke, Color, Graphics2D}
import java.awt.event.{KeyEvent, KeyListener}
import java.awt.image.BufferStrategy
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

import AsyncSocketsGame.Server.sx1
import javax.swing.{JFrame, WindowConstants}


object ClientRender {
  final val w: Int = 800
  final val h: Int = 600

  def draw(g: Graphics2D, data: GameData) = {
    g.setColor(Color.GREEN)
    g.fillRect(data.x1, data.y1, 10, 10)
    g.setColor(Color.RED)
    g.fillRect(data.x2, data.y2, 10, 10)
    g.setStroke(new BasicStroke(3))
    g.drawRect(10, 10, w - 20, h - 20)
  }


  def main(args: Array[String]): Unit = {
    val jf: JFrame = new JFrame()
    jf.setSize(w, h) //размер экрана
    jf.setResizable(false)
    jf.setUndecorated(false) //показать заголовок окна
    jf.setTitle("Моя супер программа")
    jf.setVisible(true)
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    jf.createBufferStrategy(2)


    val srv: Socket = new Socket("localhost", 1337)
    val toServer = new PrintWriter(srv.getOutputStream, true)
    val fromServer = new Scanner(srv.getInputStream)
    val data = new DataSync(fromServer)
    val keys = new Keys(toServer)
    jf.addKeyListener(keys)


    //в бесконечном цикле рисуем новый кадр
    while (true) {
      val frameLength: Long = 1000 / 60 //пытаемся работать из рассчета  60 кадров в секунду
      val start = System.currentTimeMillis()
      val bs: BufferStrategy = jf.getBufferStrategy()
      val g: Graphics2D = bs.getDrawGraphics.asInstanceOf[Graphics2D]
      g.clearRect(0, 0, jf.getWidth(), jf.getHeight())
      draw(g, data)

      bs.show()
      g.dispose()
      val end = System.currentTimeMillis()
      val len = end - start
      if (len < frameLength) {
        Thread.sleep(frameLength - len)
      }
    }
  }
}


class DataSync(fromSrv:Scanner) extends GameData {
  var x1: Int = 300
  var y1: Int = 300

  var x2: Int = 300
  var y2: Int = 400

  new Thread(() => {
    while (true) {
      fromSrv.next() match {
        case "world" =>
          x1 = fromSrv.nextInt()
          y1 = fromSrv.nextInt()
          x2 = fromSrv.nextInt()
          y2 = fromSrv.nextInt()
        case _ => println("unknown command from server")
      }
    }
  }).start()

}



class Keys(toServer:PrintWriter) extends KeyListener {
  override def keyPressed(e: KeyEvent): Unit = e.getKeyCode match {
    case KeyEvent.VK_A =>
      toServer.println("LEFT")
    case KeyEvent.VK_D =>
      toServer.println("RIGHT")
    case KeyEvent.VK_ESCAPE =>
      System.exit(0)
    case _ =>
  }

  override def keyTyped(e: KeyEvent): Unit = {}

  override def keyReleased(e: KeyEvent): Unit = {}
}



