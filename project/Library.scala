import play.sbt.PlayImport.guice
import sbt._

object Library {

  val backend = Seq(
    "mysql" % "mysql-connector-java" % "6.0.6",
    "com.typesafe.play" %% "play-slick" % "3.0.3",
    "com.typesafe.slick" %% "slick" % "3.2.1",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
    "com.typesafe.slick" %% "slick-codegen" % "3.2.1",
    "io.strongtyped" %% "active-slick" % "0.3.5",
    "com.github.pathikrit" %% "better-files" % "3.5.0"
  )

  val security = Seq(
    "io.github.nremond" %% "pbkdf2-scala" % "0.6.3"
  )

  val frontend = Seq(
    "org.webjars.bower" % "jquery" % "3.2.1",
    "org.webjars.bower" % "riot-route" % "3.1.1",
    "org.webjars.bower" % "riot" % "3.6.1",
    "org.webjars" % "Semantic-UI" % "2.3.1"
  )

  val di = Seq(guice)

  val testing = Seq("org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test)

}
