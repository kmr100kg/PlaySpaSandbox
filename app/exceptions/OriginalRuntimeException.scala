package exceptions

import play.api.libs.json.{JsValue, Json}

abstract class OriginalRuntimeException extends RuntimeException {

  private val MESSAGE_KEY = "errors"

  private val messages = Map(MESSAGE_KEY -> Seq.empty)

  def setMessages(messages: String*): OriginalRuntimeException = {
    this.messages.updated(MESSAGE_KEY, this.messages.getOrElse(MESSAGE_KEY, Seq.empty[String]) ++: messages)
    this
  }

  val getMessages: Seq[String] = {
    this.messages.getOrElse(MESSAGE_KEY, Seq.empty[String])
  }

  val json: JsValue = {
    Json.toJson(this.messages.getOrElse(MESSAGE_KEY, Seq.empty[String]))
  }

}
