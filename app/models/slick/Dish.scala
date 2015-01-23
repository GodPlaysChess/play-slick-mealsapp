package models.slick

import models.slick.Tables.Dishes
import play.api.db.DB

import scala.slick.lifted.TableQuery

/**
 * Created by Gleb on 1/4/2015.
 */

case class Dish(id: Long, name: String, likes: Int = 0)

object Dish {
  val meals = TableQuery[Dishes]

  def list = DB.withSession{ implicit session =>
    meals.sortBy(_.name.asc).list
  }
}