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

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.MealsController.addDish,
        routes.javascript.MealsController.deleteDish,
        routes.javascript.MealsController.rateDish,
        routes.javascript.AuthController.logout
      )
    ).as("text/javascript")
  }

  def allDishes = withAuth { username =>
    implicit request =>
      db.withSession { implicit session =>
        val all: List[Dish] = DishDao.list
        val scores: Map[Long, Option[Double]] = DishDao.queryAvgScore.toMap
        val userValues: Map[Long, Double] = DishDao.queryValuesFor(username)
        Ok(views.html.dishes(all, scores, userValues, mealForm, username))
      }
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

  def rateDish(id: Long, value: String) = withAuth { username =>
    implicit request =>
      DishDao.updateScore(username, id, value.toDouble)
      Redirect(routes.MealsController.allDishes())
  }
  
  def populateDatabase() = Action { implicit request =>
//      DishDao.populateDatabase(new Html5Parser().parseHtml)
      DishDao.populateDatabase(Seq.empty)
      Redirect(routes.MealsController.allDishes())
  }

}
