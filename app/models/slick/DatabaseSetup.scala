package models.slick

import play.api.db.DB

import scala.slick.driver.PostgresDriver.simple._

object DatabaseSetup {
  import play.api.Play.current
  val db = Database.forDataSource(DB.getDataSource())

}
