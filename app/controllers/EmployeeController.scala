package controllers

import java.sql.Timestamp
import java.time.LocalDateTime

import exceptions.{MasterAlreadyExistException, MasterNotExistException, OptimisticLockException}
import form.{EmployeeEditForm, EmployeeForm, EmployeeSummary}
import javax.inject._
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.EmployeeService
import slick.jdbc.JdbcProfile
import utility.MessageSharper

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future.{successful => future}

@Singleton
class EmployeeController @Inject()(employeeService: EmployeeService, messageSharper: MessageSharper, cc: ControllerComponents, val dbConfigProvider: DatabaseConfigProvider) extends
  AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  import model.Tables._

  def list = Action.async { implicit request =>
    db.run(employeeService.findAll()).map { r =>
      val summary = r.map(r => EmployeeSummary(r.employeeNumber, r.name, r.kana, r.mailAddress, r.updateDate.toString))
      Ok(Json.toJson(summary))
    }
  }

  def prepareCreate = Action.async { implicit request =>
    db.run(employeeService.findBeforeCreate()).map { r =>
      val preparedData = Map(
        "nextEmployeeNumber" -> r.toString
      )
      Ok(Json.toJson(preparedData))
    }
  }

  def create = Action.async { implicit request =>
    val form = EmployeeForm.form.bindFromRequest
    form.fold(error => {
      future(BadRequest(messageSharper.asError(error.errorsAsJson)))
    }, success => {
      val employeeRow = EmployeeRow(
        employeeNumber = success.employeeNumber,
        name = success.name,
        kana = success.kana,
        mailAddress = success.mailAddress,
        password = success.password,
        updateDate = Timestamp.valueOf(LocalDateTime.now())
      )
      db.run(employeeService.create(employeeRow)).map { _ =>
        Created(messageSharper.asSuccess(s"${success.name}さんを登録しました！"))
      } recover {
        case _:MasterAlreadyExistException =>
          BadRequest(messageSharper.asError(Map("DBエラー" -> Seq("既に登録されています"))))
        case e:Throwable =>
          Logger.error("システムエラー", e)
          InternalServerError
      }
    })
  }

  def edit = Action.async { implicit request =>
    val form = EmployeeEditForm.form.bindFromRequest
    form.fold(error => {
      future(BadRequest(messageSharper.asErrorReshaped(error.errorsAsJson)))
    }, success => {
      val employeeRow = EmployeeRow(
        employeeNumber = success.employeeNumber,
        name = success.name,
        kana = success.kana,
        mailAddress = success.mailAddress,
        // 空の場合は更新前にDBの値に置き換える
        password = success.newPassword.getOrElse(""),
        updateDate = Timestamp.valueOf(success.updateDate)
      )
      db.run(employeeService.edit(employeeRow)).map { _ =>
        Ok(messageSharper.asSuccess(s"${employeeRow.name}さんを更新しました！"))
      } recover {
        case _: MasterNotExistException =>
          BadRequest(messageSharper.asError(Map("DBエラー" -> Seq("社員が存在しません"))))
        case _: OptimisticLockException =>
          BadRequest(messageSharper.asError(Map("DBエラー" -> Seq("あなたが更新する前に他の誰かによって更新されています。画面を再読み込みしてデータを確認してください"))))
        case e: Throwable =>
          Logger.error("システムエラー", e)
          InternalServerError
      }
    })
  }

  def delete(employeeNumber: Int) = Action.async { implicit request =>
    db.run(employeeService.delete(employeeNumber)).map { name =>
      Ok(messageSharper.asSuccess(s"${name}さんを削除しました！"))
    } recover {
      case _: MasterNotExistException =>
        BadRequest(messageSharper.asError(Map("DBエラー" -> Seq("社員が存在しません"))))
      case e: Throwable =>
        Logger.error("システムエラー", e)
        InternalServerError
    }
  }
}
