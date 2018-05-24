package utility

import javax.inject.{Inject, Singleton}
import play.api.i18n._
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable

/**
  * メッセージを色々整形するクラス。
  * @param messagesApi コントローラでI18nSupportを継承すれば何もしなくてOK
  */
@Singleton
class MessageSharper @Inject()(implicit val messagesApi: MessagesApi) {

  private val SUCCESS_KEY = "successes"
  private val ERROR_KEY = "errors"

  implicit val customMessagesProvider: MessagesProvider = new MessagesProvider {
    override def messages: Messages = {
      MessagesImpl(Lang.defaultLang, messagesApi)
    }
  }

  def reshape(jsValue: JsValue): JsValue = {
    val treeMap = new mutable.TreeMap[String, JsValue]()
    val linkedMap = new mutable.LinkedHashMap[String, JsValue]
    jsValue.as[JsObject].value.map { case (k, v) => treeMap.put(Messages(k), v) }
    treeMap.foreach { case (k, v) => linkedMap.put(k.replaceAll(".*_", ""), v) }
    Json.toJson(linkedMap)
  }

  def asSuccess(jsValue: JsValue): JsValue = {
    Json.toJson(Map(SUCCESS_KEY -> reshape(jsValue)))
  }

  def asSuccess(message: String): JsValue = {
    Json.toJson(Map(SUCCESS_KEY -> message))
  }

  def asSuccess(messages: Map[String, Seq[String]]): JsValue = {
    Json.toJson(Map(SUCCESS_KEY -> messages))
  }

  def asError(jsValue: JsValue): JsValue = {
    Json.toJson(Map(ERROR_KEY -> reshape(jsValue)))
  }

  def asError(message: String): JsValue = {
    Json.toJson(Map(ERROR_KEY -> message))
  }

  def asError(messages: Map[String, Seq[String]]): JsValue = {
    Json.toJson(Map(ERROR_KEY -> messages))
  }

}

