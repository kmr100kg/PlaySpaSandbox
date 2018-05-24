package form

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._

case class EmployeeForm(employeeNumber: Int, name: String, kana: String, mailAddress: String, password: String, passwordConfirm: String)

case class EmployeeEditForm(employeeNumber: Int, name: String, kana: String, mailAddress: String, newPassword: Option[String], newPasswordConfirm: Option[String], updateDate: String)

object EmployeeForm {
  val form = Form(
    mapping(
      "employeeNumber" -> number,
      "name" -> nonEmptyText,
      "kana" -> nonEmptyText.verifying(pattern("[\u30A1-\u30FC]*".r, error = "must.be.zenkaku.kana")),
      "mailAddress" -> email,
      "password" -> nonEmptyText(minLength = 8),
      "passwordConfirm" -> nonEmptyText(minLength = 8)
    )(EmployeeForm.apply)(EmployeeForm.unapply).verifying(
      "error.password.not.equals", form => form.password == form.passwordConfirm
    )
  )
}

object EmployeeEditForm {
  val form = Form(
    mapping(
      "employeeNumber" -> number,
      "name" -> nonEmptyText,
      "kana" -> nonEmptyText.verifying(pattern("[\u30A1-\u30FC]*".r, error = "must.be.zenkaku.kana")),
      "mailAddress" -> email,
      "newPassword" -> optional(text(minLength = 8)),
      "newPasswordConfirm" -> optional(text(minLength = 8)),
      "updateDate" -> nonEmptyText
    )(EmployeeEditForm.apply)(EmployeeEditForm.unapply).verifying(
      "error.password.not.equals", form => form.newPassword.getOrElse("") == form.newPasswordConfirm.getOrElse("")
    )
  )
}
