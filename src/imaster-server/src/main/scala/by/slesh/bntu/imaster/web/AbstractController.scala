package by.slesh.bntu.imaster.web

import by.slesh.bntu.imaster.security.AuthenticationSupport
import org.scalatra.json._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions

abstract class AbstractController extends ScalatraServlet with AuthenticationSupport with JacksonJsonSupport with FutureSupport {
  private val logger = LoggerFactory.getLogger(getClass)
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  before() {
    contentType = formats("json")
    logger.info(requestToString)
  }

  private def requestToString = {
    var message: String = "request, url=" + request.getRequestURL + ", uri=" + request.getRequestURI + ", path=" + request.getPathInfo
    val names = JavaConversions.enumerationAsScalaIterator(request.getParameterNames)
    if (names.nonEmpty) {
      message += ", parameters="
      for (name <- names) {
        message += name + ":" + params(name) + "   "
      }
    }
    message
  }
}
