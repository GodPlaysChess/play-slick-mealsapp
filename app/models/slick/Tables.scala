package models.slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ProvenShape

/**
 * Created by Gleb on 1/3/2015.
 */
object Tables {
  class Dishes(tag: Tag) extends Table[models.slick.Dish](tag, "DISHES") {
    def id = column[Long]("DISH_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("DISH_NAME")
    def likes = column[Int]("LIKES")

    override def * : ProvenShape[Dish] = (id, name, likes) <> (Dish.tupled, Dish.unapply)
  }

  val dishes: TableQuery[Dishes] = TableQuery[Dishes]

  /**
   *            Dish Scores
   *   sId    |   dishId    |     value
   *    1     |      1      |       5
   *    2     |      1      |       6
   *    3     |      2      |       7
   */

  class DishScore(tag: Tag) extends Table[(Long, Long, Double)](tag, "DISH_SCORE") {
    def sId = column[Long]("SCORE_ID", O.PrimaryKey, O.AutoInc)
    def dishId = column[Long]("DISH_ID")
    def value = column[Double]("VALUE")

    override def * = (sId, dishId, value)
    def dish = foreignKey("DISH_FK", dishId, dishes)(_.id)
  }
  
  val dishscores = TableQuery[DishScore]
  
  implicit class DishExtentions[C[_]](q: Query[Dishes, Double, C]) {
    def withScores = q.join(dishscores).on(_.id === _.dishId)
  }
  object Examples {
    val joinCondition = (d: Dishes, s: DishScore) => d.id === s.dishId
    val joinQuery = dishes join dishscores on joinCondition
    // then can do  joinQuery foreach println
  }
}




