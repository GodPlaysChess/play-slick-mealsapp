import models.slick.Dish$
import models.slick.Tables._
import play.api._
import scala.slick.driver.H2Driver.simple._
import models.slick.DatabaseSetup.db

/**
 * Created by Gleb on 1/3/2015.
 */
object Global extends GlobalSettings {
  
//  override def onStart(app: Application) {
//    InitialData.insert()
//  }
//
//  object InitialData {
//    def insert() = {
//      db withSession {
//        implicit session =>
//          dishes.ddl.create
//          dishes += Dish(1, "One Dish")
//          dishscores.ddl.create
//      }
//    }
//
//  }

}
