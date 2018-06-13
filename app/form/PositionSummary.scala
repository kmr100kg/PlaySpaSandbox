package form

import play.api.libs.json.{Json, OFormat}

case class PositionSummary(id: Long, name: String)

object PositionSummary {
  implicit val format: OFormat[PositionSummary] = Json.format[PositionSummary]
}




