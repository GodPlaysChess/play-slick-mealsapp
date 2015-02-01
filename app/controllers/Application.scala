package controllers

import models.slick.DatabaseSetup.db
import models.slick.{Dish, DishDao}
import play.api.Routes
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller {

  val mealForm = Form("name" -> nonEmptyText)

  val evalForm = Form("mark" -> number(min = 0, max = 2))

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.Application.addDish,
        routes.javascript.Application.addLike,
        routes.javascript.Application.deleteDish
      )
    ).as("text/javascript")
  }

  def index = Action {
    Ok(views.html.index())
  }

  def allDishes = Action { implicit request =>
    db.withSession { implicit session =>
      val all: List[Dish] = DishDao.list
      val scores: Map[Long, Option[Double]] = DishDao.queryAvgScore.toMap
      Ok(views.html.dishes(all, scores, mealForm))
    }
  }
  
  def overview(ord: String) = Action { implicit request =>
    db.withSession { implicit session =>
      val scores: Map[Long, Option[Double]] = DishDao.queryAvgScore.toMap
      val all: List[Dish] = if (ord == "asc") DishDao.list else DishDao.listDesc
      Ok(views.html.dishes(all, scores, mealForm))
    }
  }
  
  def addDish() = Action { implicit request =>
    val meal = mealForm.bindFromRequest.get
    DishDao.add(meal)
    Redirect(routes.Application.allDishes())
  }

  def deleteDish(id: Long) = Action { implicit request =>
    DishDao.delete(id)
    Redirect(routes.Application.allDishes())
  }

  def addLike(id: Long) = Action { implicit request =>
    DishDao.addLike(id)
    Redirect(routes.Application.allDishes())
  }

  def evaluateDish(id: Long) = Action { implicit request =>
    DishDao.updateScore(id, evalForm.bindFromRequest().get)
    Redirect(routes.Application.allDishes())
  }

}
