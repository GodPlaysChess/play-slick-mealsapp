package controllers

import models.slick.DatabaseSetup._
import models.slick.{Dish, DishDao}
import play.api.Routes
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Controller, Action}
import security.Secured

object MealsController extends Controller with Secured {
  val mealForm = Form("name" -> nonEmptyText)

  val evalForm = Form("mark" -> number(min = 0, max = 2))

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.MealsController.addDish,
        routes.javascript.MealsController.addLike,
        routes.javascript.MealsController.deleteDish
      )
    ).as("text/javascript")
  }

  def allDishes = withAuth { username =>
    implicit request =>
      db.withSession { implicit session =>
        val all: List[Dish] = DishDao.list
        val scores: Map[Long, Option[Double]] = DishDao.queryAvgScore.toMap
        Ok(views.html.dishes(all, scores, mealForm))
      }
  }

  def overview(ord: String) = withAuth { username =>
    implicit request =>
      val scores: Map[Long, Option[Double]] = DishDao.queryAvgScore.toMap
      val all: List[Dish] = if (ord == "asc") DishDao.list else DishDao.listDesc
      Ok(views.html.dishes(all, scores, mealForm))
  }

  def addDish() = withAuth { username =>
    implicit request =>
      val meal = mealForm.bindFromRequest.get
      DishDao.add(meal)
      Redirect(routes.MealsController.allDishes())
  }

  def deleteDish(id: Long) = withAuth { username =>
    implicit request =>
      DishDao.delete(id)
      Redirect(routes.MealsController.allDishes())
  }

  def addLike(id: Long) = withAuth { username =>
    implicit request =>
      DishDao.addLike(id)
      Redirect(routes.MealsController.allDishes())
  }

  def evaluateDish(id: Long) = withAuth { username =>
    implicit request =>
      DishDao.updateScore(id, evalForm.bindFromRequest().get)
      Redirect(routes.MealsController.allDishes())
  }

}
