package filters

import javax.inject._
import play.api.Logger
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * This is a simple filter that adds a header to all requests. It's
 * added to the application's list of filters by the
 * [[Filters]] class.
 *
 * @param ec This class is needed to execute code asynchronously.
 * It is used below by the `map` method.
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
