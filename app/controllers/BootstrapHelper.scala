package controllers

import views.html.helper.FieldConstructor
import views.html.helper.twitterBootstrap.twitterBootstrapFieldConstructor

object BootstrapHelper {

  implicit val fields = FieldConstructor(twitterBootstrapFieldConstructor.f)

}