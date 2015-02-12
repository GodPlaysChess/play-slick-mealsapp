import play.api._

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
