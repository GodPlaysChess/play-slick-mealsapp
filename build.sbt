name := """play-scala-intro"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  "org.sorm-framework" % "sorm" % "0.3.15",
  "com.h2database" % "h2" % "1.4.177",
  "org.webjars" % "bootstrap" % "3.0.0",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

libraryDependencies += jdbc

