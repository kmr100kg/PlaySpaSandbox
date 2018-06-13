package form

import play.api.libs.json.{Json, OFormat}

case class DepartmentSummary(id: Long, name: String)

object DepartmentSummary {
  implicit val format: OFormat[DepartmentSummary] = Json.format[DepartmentSummary]
}


