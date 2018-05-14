package controllers

import javax.inject._
import form.{EmployeeForm, EmployeeSummary}
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.mvc._
import services.{EmployeeService, MasterAlreadyExistException, MasterNotExistException}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future.{successful => future}

@Singleton
class EmployeeController @Inject()(employeeService: EmployeeService, cc: ControllerComponents, val dbConfigProvider: DatabaseConfigProvider) extends
  AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  import model.Tables._

  def list = Action.async { implicit request =>
    db.run(employeeService.findAll()).map { r =>
      val summary = r.map(r => EmployeeSummary(r.employeeNumber, r.name, r.kana, r.mailAddress))
      Ok(Json.toJson(summary))
    }
  }

  def create = Action.async { implicit request =>
    val form = EmployeeForm.form.bindFromRequest
    form.fold(error => {
      val messages = error.errors.map { e =>
        val message = cc.messagesApi.preferred(request)
        (message(e.key), message(e.message))
      }
      future(BadRequest(Json.toJson(Map("errors" -> messages))))
    }, success => {
      val employeeRow = EmployeeRow(
        employeeNumber = success.employeeNumber,
        name = success.name,
        kana = success.kana,
        mailAddress = success.mailAddress,
        password = success.password
      )
      db.run(employeeService.create(employeeRow)).map { _ =>
        Ok(Json.toJson(Map("successes" -> s"${success.name}さんを登録しました！")))
      } recover {
        case _:MasterAlreadyExistException =>
          BadRequest(Json.toJson(Map("errors" -> Seq(("DBエラー", "既に登録されています")))))
        case e:Throwable =>
          Logger.error("システムエラー", e)
          InternalServerError
      }
    })
  }

  def prepareEdit = Action.async { implicit request =>
    future(Ok)
  }

  def delete(employeeNumber: Int) = Action.async { implicit request =>
    db.run(employeeService.delete(employeeNumber)).map { name =>
      Ok(Json.toJson(Map("successes" -> s"${name}さんを削除しました！")))
    } recover {
      case _: MasterNotExistException =>
        BadRequest(Json.toJson(Map("errors" -> "社員が存在しません")))
      case e: Throwable =>
        Logger.error("システムエラー", e)
        InternalServerError
    }
  }
}
