package form

import play.api.libs.json.{Json, OFormat}

case class EmployeeSummary(id: Long, name: String)

object EmployeeSummary {
  implicit val format: OFormat[EmployeeSummary] = Json.format[EmployeeSummary]
}
