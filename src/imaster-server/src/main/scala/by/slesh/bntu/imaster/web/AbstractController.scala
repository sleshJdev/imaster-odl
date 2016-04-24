package by.slesh.bntu.imaster.web

import by.slesh.bntu.imaster.security.AuthenticationSupport
import by.slesh.bntu.imaster.web.json.StringToInt
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions

abstract class AbstractController
  extends ScalatraServlet
    with AuthenticationSupport
    with JacksonJsonSupport
    with FutureSupport {

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  protected implicit lazy val jsonFormats: Formats = DefaultFormats + StringToInt
  private val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
    logger.info(requestToString)
  }

  private def requestToString = {
    var message: String = "%s url=%s, uri=%s, path=%s"
      .format(request.getProtocol, request.getRequestURL, request.getRequestURI, request.getPathInfo)
    val names = JavaConversions.enumerationAsScalaIterator(request.getParameterNames)
    if (names.nonEmpty) {
      message += ", parameters="
      for (name <- names) { message += name + "::" + params(name) + " "}
    }
    message
  }
}
