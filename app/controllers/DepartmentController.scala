package controllers

import form.DepartmentSummary
import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.DepartmentService
import slick.jdbc.JdbcProfile
import utility.MessageSharper

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class DepartmentController @Inject()(departmentService: DepartmentService, messageSharper: MessageSharper, cc: ControllerComponents, val dbConfigProvider: DatabaseConfigProvider) extends
  AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def list = Action.async { implicit request =>
    db.run(departmentService.findAll()).map { r =>
      val summary = r.map(r => DepartmentSummary(r.id, r.name))
      Ok(Json.toJson(summary))
    }
  }
}
