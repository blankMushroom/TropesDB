package httpServer

object PageTemplate {
  def genPage(content:String):String =
    s"""
       |<!DOCTYPE html>
       |<html lang="en">
       |<head>
       |    <meta charset="UTF-8">
       |    <title>My super site</title>
       |    <link rel="stylesheet" href="styles.css">
       |</head>
       |<body>
       |<div class="container">
       |    <header class="header">
       |        <h1>OOO Enterprise solutions</h1>
       |    </header>
       |    <article class="content">
       |    $content
       |
       |    </article>
       |    <div class="menu">
       |        <ul>
       |            <li><a href="/list"> list</a></li>
       |            <li><a href="/add"> add</a></li>
       |            <li> three</li>
       |            <li> four</li>
       |        </ul>
       |    </div>
       |    <div class="ad">
       |        <h2><a href="http://google.com/?q=do%20a%20barrel%20roll">BUY NOW</a></h2>
       |    </div>
       |    <footer class="footer">
       |        <i>880055535353</i>
       |        <b>2049-present</b>
       |    </footer>
       |</div>
       |</body>
       |</html>
       |""".stripMargin
}
