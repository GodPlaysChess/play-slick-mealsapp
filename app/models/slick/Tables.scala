package models.slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ProvenShape

object Tables {
  class Dishes(tag: Tag) extends Table[models.slick.Dish](tag, "dishes") {
    def id = column[Long]("dish_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("dish_name")
    def code = column[String]("code")
    def weigth = column[Int]("weigth")
    def calories = column[String]("calories")
    def proteins = column[Int]("proteins")
    def carbs = column[Int]("carbs")
    def fat = column[Int]("fat")
    def price = column[String]("price")

    override def * : ProvenShape[Dish] = (id, name, code, weigth, calories, proteins, carbs, fat, price) <> (Dish.tupled, Dish.unapply)
    def scores = foreignKey("dish_fk", id, DishDao.dishscores)(_.dishId, onDelete = ForeignKeyAction.Cascade)
  }

  /**
   *            Dish Scores
   *   username    |   dishId    |     value
   *       1       |      1      |       5
   *       2       |      1      |       6
   *       3       |      2      |       7
   */

  class DishScore(tag: Tag) extends Table[(String, Long, Double)](tag, "dish_scores") {
    def username = column[String]("username")
    def dishId = column[Long]("dish_id")
    def value = column[Double]("value")

    override def * = (username, dishId, value)
    def pk = primaryKey("pk_score", (username, dishId))
  }
  
  class Users(tag: Tag) extends Table[User](tag, "users") {
    def userid = column[Long]("user_id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")
    
    override def * : ProvenShape[User] = (userid, username, password) <> (User.tupled, User.unapply)
  }
  
}




