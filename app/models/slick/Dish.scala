package models.slick

import models.slick.DatabaseSetup.db
import models.slick.Tables._


import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted
import scala.slick.lifted.TableQuery

//TODO add price per gramm, price per calorie, etc..
case class Dish(id: Long = 0, name: String = "", code: String = "H00000", weight: Int = 0, calories: String = "0", proteins: Int = 0, carbs: Int = 0, fat: Int = 0, price: String = "0")

object DishDao {

  val dishes: TableQuery[Dishes] = TableQuery[Dishes]
  val dishscores = TableQuery[DishScore]

  def list: List[Dish] = db.withSession { implicit session =>
    dishes.sortBy(_.name.asc).list
  }

  def listDesc: List[Dish] = db.withSession { implicit session =>
    dishes.sortBy(_.name.desc).list
  }

  def queryValuesFor(username: String): Map[Long, Double] = db withSession { implicit session =>
    dishscores.filter(_.username === username).map(row => (row.dishId, row.value)).toMap
  }

  def add(name: String) = {
    db.withSession { implicit session =>
      dishes += Dish(0, name)
    }
  }

  def delete(id: Long) = {
    db.withSession { implicit session =>
      dishscores.filter(_.dishId === id).delete
      dishes.filter(_.id === id).delete
    }
  }

  def queryAvgScore: Set[(Long, Option[Double])] = {
    db withSession { implicit session =>
      dishscores
        .groupBy(_.dishId) // query: (dishid, Seq[ ( sId, dishId, value) ] )
        .map { case (dish, scoreSeq) => (dish, scoreSeq.map(_.value).avg)} // extracting a value, and mapping average
        .buildColl[Set]
    }
  }

  def updateScore(username: String, dishId: Long, score: Double) = {
    db.withSession { implicit session =>
      val entry: lifted.Query[DishScore, (String, Long, Double), Seq] = dishscores.withFilter(_.username === username).withFilter(_.dishId === dishId)
      entry.firstOption match {
        case Some(_) => entry.map(_.value).update(score)
        case None => dishscores.insert(username, dishId, score)
      }
    }
  }

  def scores(id: Long): Seq[Double] = {
    db.withSession { implicit session =>
      dishscores.filter(_.dishId === id).map(_.value).buildColl[Seq]
    }
  }

  def populateDatabase(entries: Seq[Dish]) = {
    db withSession { implicit session =>
      val allentriesNames: List[String] = dishes.map(_.name).list
      val newEntries: Seq[Dish] = entries.filterNot(d => allentriesNames.contains(d.name))
      // only which does not exists
      dishes ++= newEntries
    }
  }

  implicit class DishExtentions[C[_]](q: Query[Dishes, Double, C]) {
    def withScores = q.join(dishscores).on(_.id === _.dishId)
  }

  object Examples {
    val joinCondition = (d: Dishes, s: DishScore) => d.id === s.dishId
    val joinQuery = dishes join dishscores on joinCondition
    // then can do  joinQuery foreach println
  }


}