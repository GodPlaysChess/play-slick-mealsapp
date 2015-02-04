package models.slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape}

object Tables {
  class Dishes(tag: Tag) extends Table[models.slick.Dish](tag, "DISHES") {
    def id = column[Long]("DISH_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("DISH_NAME")
    def likes = column[Int]("LIKES") 
    
    override def * : ProvenShape[Dish] = (id, name, likes) <> (Dish.tupled, Dish.unapply)
    def scores = foreignKey("DISH_FK", id, DishDao.dishscores)(_.dishId)
  }

  /**
   *            Dish Scores
   *   username    |   dishId    |     value
   *       1       |      1      |       5
   *       2       |      1      |       6
   *       3       |      2      |       7
   */

  class DishScore(tag: Tag) extends Table[(String, Long, Double)](tag, "DISH_SCORES") {
    def username = column[String]("USERNAME")
    def dishId = column[Long]("DISH_ID")
    def value = column[Double]("VALUE")

    override def * = (username, dishId, value)
    def pk = primaryKey("pk_score", (username, dishId))
  }
  
  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("USERNAME")
    def password = column[String]("PASSWORD")
    
    override def * : ProvenShape[User] = (id, name, password) <> (User.tupled, User.unapply)
  }
  

  

}




