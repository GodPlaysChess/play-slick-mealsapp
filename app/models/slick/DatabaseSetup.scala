package models.slick

import play.api.db.DB

import scala.slick.driver.PostgresDriver.simple._

/**
 * Created by Gleb on 1/3/2015.
 */
object DatabaseSetup {
  import play.api.Play.current
  val db = Database.forDataSource(DB.getDataSource())
//  val db = Database.forURL("jdbc:postgresql://localhost/meal_test_database",
//    driver = "org.postgresql.Driver", user = "postgres", password = "warcraft")
}
