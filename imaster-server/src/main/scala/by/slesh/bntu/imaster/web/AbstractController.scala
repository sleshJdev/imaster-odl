package by.slesh.bntu.imaster.web

import by.slesh.bntu.imaster.security.AuthenticationSupport
import org.scalatra.ScalatraServlet
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions

abstract class AbstractController extends ScalatraServlet with AuthenticationSupport {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  private val logger = LoggerFactory.getLogger(getClass)

  before() {
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
