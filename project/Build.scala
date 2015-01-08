/*
import play.PlayScala
import sbt._
import Keys._

object MyAppBuild extends Build {
  lazy val root = (project in file(".")).enablePlugins(PlayScala)

  val appName = "play-scala-intro"
  val appVersion = "1.0-SNAPSHOT"
  val scalaVersion = "2.10.4"

  val appDependencies = Seq(
    "org.sorm-framework" % "sorm" % "0.3.15",
    "com.h2database" % "h2" % "1.4.177",
    "org.webjars" % "bootstrap" % "3.3.1"
  )

  // Only compile the bootstrap bootstrap.less file and any other *.less file in the stylesheets directory
  def customLessEntryPoints(base: File): PathFinder =
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
      (base / "app" / "assets" / "stylesheets" * "*.less")

  val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
    version := appVersion,
    libraryDependencies ++= appDependencies
//    lessEntryPoints <<= baseDirectory(customLessEntryPoints)
  )

}


*/
