package form

import play.api.libs.json.{Json, OFormat}

case class EmployeeSummary(employeeNumber: Long, name: String, kana: String, mailAddress: String, password: String)

object EmployeeSummary {
  implicit val format: OFormat[EmployeeSummary] = Json.format[EmployeeSummary]
}
