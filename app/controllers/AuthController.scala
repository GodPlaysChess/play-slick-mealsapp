package controllers

import models.slick.UserDao
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import views.html

object AuthController extends Controller {
  //lol, if not lazy then compile error
  lazy val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying("Invalid", result => result match {
      case (name, password) =>
        UserDao.authenticate(name, password) == 1
      case _ => false
    })
  )

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.MealsController.allDishes()) withSession ("email" -> user._1)
    )
  }

  def logout = Action {
    Redirect(routes.AuthController.login()).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }


}
