package controllers

import models.slick.UserDao
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Request, AnyContent, Action, Controller}
import views.html

object AuthController extends Controller {
  //lol, if not lazy then compile error
  lazy val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) /*verifying("Invalid", result => result match {
      case (name, password) =>
        UserDao.authenticate(name, password) == 1
      case _ => false
    })*/
  )

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def authenticate = Action { implicit request =>
    request.body.asFormUrlEncoded.get("action").headOption match {
      case Some("login") => oldUserAuth
      case Some("new") => newUserAuth
      case _ => BadRequest("This action is not allowed")
    }
  }

  private def oldUserAuth(implicit req: Request[AnyContent]) = {
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => UserDao.findByName(user._1) match {
        case Some(username) => Redirect(routes.MealsController.allDishes()) withSession ("email" -> user._1)
        case None => BadRequest("This user does not exist")
      }
    )
  }

  def newUserAuth(implicit req: Request[AnyContent]) = {
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => UserDao.findByName(user._1) match {
        case Some(username) => BadRequest("This user is already created") 
        case None =>
          UserDao.create(user._1, user._2)
          Redirect(routes.MealsController.allDishes()) withSession ("email" -> user._1)
      }
    )
  }

  def logout = Action {
    Redirect(routes.AuthController.login()).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }


}
