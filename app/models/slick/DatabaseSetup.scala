package models.slick

import scala.slick.driver.H2Driver.simple._

/**
 * Created by Gleb on 1/3/2015.
 */
object DatabaseSetup {
  val db = Database.forURL("jdbc:h2:mem:play", driver = "org.h2.Driver")
}
