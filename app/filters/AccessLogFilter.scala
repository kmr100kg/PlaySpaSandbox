package filters

import javax.inject._
import play.api.Logger
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * アクセスログフィルタ。
 */
@Singleton
class AccessLogFilter @Inject()(implicit ec: ExecutionContext) extends EssentialFilter {
  override def apply(next: EssentialAction) = EssentialAction { request =>
    next(request).map { result =>
      val log = s"${request.id} ${request.host} ${request.remoteAddress} ${request.contentType.getOrElse("")} " +
        s"${request.method} ${request.uri} ${request.rawQueryString}"
      Logger.apply("access").logger.info(log)
      result
    }
  }
}
