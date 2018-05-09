package form

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._

case class EmployeeForm(employeeNumber: Int, name: String, kana: String, mailAddress: String, password: String, passwordConfirm: String)

object EmployeeForm {
  val form = Form(
    mapping(
      "employeeNumber" -> number,
      "name" -> nonEmptyText,
      "kana" -> nonEmptyText.verifying(pattern("[\u30A1-\u30FC]*".r, error = "must.be.zenkaku.kana")),
      "mailAddress" -> email,
      "password" -> nonEmptyText,
      "passwordConfirm" -> nonEmptyText
    )(EmployeeForm.apply)(EmployeeForm.unapply).verifying(
      "error.password.not.equals", form => form.password == form.passwordConfirm
    )
  )
}
