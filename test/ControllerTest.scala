import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * コントローラのテスト。
  */
class ControllerTest extends PlaySpec with GuiceOneAppPerSuite {

  "EmployeeController#list" should {

    "normal request" in {
      route(app, FakeRequest(GET, "/list")).map(status) mustBe Some(OK)
    }

    "request with query string" in {
      route(app, FakeRequest(GET, "/list?sample=0")).map(status) mustBe Some(OK)
    }

  }

  "EmployeeController#create" should {

    "normal request" in {
      val postValue = Json.toJson(
        Map(
          "employeeNumber" -> "9999",
          "name" -> "テスト太郎",
          "kana" -> "テストタロウ",
          "mailAddress" -> "test@mail.co.jp",
          "password" -> "test1234",
          "passwordConfirm" -> "test1234"
        )
      )
      route(app, FakeRequest(POST, "/create").withJsonBody(postValue)).map(status) mustBe Some(CREATED)
    }

    "abnormal request (validation error)" in {
      val postedValue = Json.toJson(
        Map(
          "employeeNumber" -> "9999",
          "name" -> "",
          "kana" -> "テストタロウ",
          "mailAddress" -> "test@mail.co.jp",
          "password" -> "test1234",
          "passwordConfirm" -> "test1234"
        )
      )
      val result = route(app, FakeRequest(POST, "/create").withJsonBody(postedValue))
      result.map(status) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) contains "未入力です"
    }

  }

}
