name := """play-slick-mealsapp"""

version := "1.0"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  "com.h2database" % "h2" % "1.4.177",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "jquery-ui" % "1.11.0-1",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "nu.validator.htmlparser" % "htmlparser" % "1.2.1",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"
)


