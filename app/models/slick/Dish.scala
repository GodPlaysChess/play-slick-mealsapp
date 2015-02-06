package models.slick

import models.slick.Tables._
import scala.slick.driver.H2Driver.simple._
import DatabaseSetup.db


import scala.slick.lifted.TableQuery

case class Dish(id: Long, name: String, likes: Int = 0)

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
//ду муш д блумешток гизе зуншт фэдед р дэ

  def add(name: String) = {
    db.withSession { implicit session =>
      dishes += Dish(0, name)
    }
  }

  def delete(id: Long) = {
    db.withSession { implicit session =>
      //TODO remove the meal from scores as well.
      dishes.filter(_.id === id).delete
    }
  }

  def addLike(id: Long) = {
    db.withSession { implicit session =>
      val q = dishes.filter(_.id === id).map(_.likes)
      q.update(q.first + 1)
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
        dishscores.insertOrUpdate(username, dishId, score)
    }
  }
  
  def scores(id: Long): Seq[Double] = {
    db.withSession { implicit session =>
      dishscores.filter(_.dishId === id).map(_.value).buildColl[Seq]
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