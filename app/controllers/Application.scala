package controllers

import models.slick.{Dish, DatabaseSetup}
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import scala.slick.driver.H2Driver.simple._
import models.slick.Tables._
import DatabaseSetup.db

object Application extends Controller {

  val mealForm = Form("name" -> nonEmptyText)

  val evalForm = Form("mark" -> number(min = 0, max = 2))

  def index = Action {
    Ok(views.html.index())
  }

  def allDishes = Action { implicit request =>
    db.withSession { implicit session =>
      val all: List[Dish] = dishes.list
      val scores: Map[Long, Option[Double]] = queryAvg.toMap
      Ok(views.html.dishes(all, scores, mealForm)) // thats on scala side. I want on DB side!
    }
  }

  // on scala collection side.. but I want to use SQL agregate statements.
  private def queryScores /*: Map[Long, List[Double]]*/ = {
    //    db withSession { implicit session =>
    //      dishscores
    //        .groupBy(_.dishId)
    //        .map { case (id, scores) => (id, scores.map(_.value)) }
    //        .buildColl[Set]
    //    }
  }

  //SQL
  private def queryAvg: Set[(Long, Option[Double])] = {
    db withSession { implicit session =>
      dishscores
        .groupBy(_.dishId) // query: (dishid, Seq[ ( sId, dishId, value) ] )
        .map { case (dish, scoreSeq) => (dish, scoreSeq.map(_.value).avg)} // extracting a value, and mapping average
        .buildColl[Set]
    }
  }

  def addDish() = Action { implicit request =>
    val meal = mealForm.bindFromRequest.get
    db.withSession { implicit session =>
      dishes += Dish(0, meal)
    }
    Redirect(routes.Application.allDishes())
  }

  def deleteDish(id: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      dishes.filter(_.id === id).delete
    }
    Redirect(routes.Application.allDishes())
  }

  def addLike(id: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      //      dishes.filter(_.id === id).map(_.likes + 1).execute
      val q = dishes.filter(_.id === id).map(_.likes)
      q.update(q.first + 1)
      //      val l = dishes.filter(_.id === id).map(_.likes).first       // sure it can be done much better
      //      dishes.filter(_.id === id).map(_.likes).update(l + 1)
    }
    Redirect(routes.Application.allDishes())
  }

  def evaluateDish(id: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      val score = evalForm.bindFromRequest().get
      dishscores +=(0, id, score)
    }
    Redirect(routes.Application.allDishes())
  }

  def createTables = Action { implicit request =>
    db withSession {
      implicit session =>
        dishes.ddl.create
        dishes += Dish(1, "One Dish")
        dishscores.ddl.create
    }
    Redirect(routes.Application.index())
  }

}
