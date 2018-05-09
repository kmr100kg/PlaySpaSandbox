package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.Future.{successful => future}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action.async { implicit request =>
    future(Ok(views.html.index()))
  }

}
