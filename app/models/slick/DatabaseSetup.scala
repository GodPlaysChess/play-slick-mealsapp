package models.slick

import play.api.Play

import scala.slick.driver.PostgresDriver.simple._

/**
 * Created by Gleb on 1/3/2015.
 */
object DatabaseSetup {
//  val db = Database.forURL("jdbc:h2:mem:play", driver = "org.h2.Driver")
  private[this] val url = Play.current.configuration.getString("db.default.url").get
  private[this] val driv = Play.current.configuration.getString("db.default.driver").get
  val db = Database.forURL(url, driver = driv)
}
