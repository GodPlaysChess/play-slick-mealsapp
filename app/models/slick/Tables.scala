package models.slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape}

object Tables {
  class Dishes(tag: Tag) extends Table[models.slick.Dish](tag, "DISHES") {
    def id = column[Long]("DISH_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("DISH_NAME")
    def likes = column[Int]("LIKES") 
    
    override def * : ProvenShape[Dish] = (id, name, likes) <> (Dish.tupled, Dish.unapply)
    def scores = foreignKey("DISH_FK", id, DishDao.dishscores)(_.sId)
  }

  /**
   *            Dish Scores
   *   sId    |   dishId    |     value
   *    1     |      1      |       5
   *    2     |      1      |       6
   *    3     |      2      |       7
   */

  class DishScore(tag: Tag) extends Table[(Long, Long, Double)](tag, "DISH_SCORES") {
    def sId = column[Long]("SCORE_ID", O.PrimaryKey, O.AutoInc)
    def dishId = column[Long]("DISH_ID")
    def value = column[Double]("VALUE")

    override def * = (sId, dishId, value)
  }
  

  

}




