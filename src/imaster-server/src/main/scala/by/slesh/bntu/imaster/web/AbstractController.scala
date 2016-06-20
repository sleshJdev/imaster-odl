package by.slesh.bntu.imaster.web

import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

import by.slesh.bntu.imaster.security.AuthenticationSupport
import by.slesh.bntu.imaster.web.json.{DateToString, StringToInt}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig}
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions

abstract class AbstractController
  extends ScalatraServlet
    with AuthenticationSupport
    with JacksonJsonSupport
    with FutureSupport
    with FileUploadSupport {

  override val logger = LoggerFactory.getLogger(getClass)

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  protected implicit lazy val jsonFormats: Formats = DefaultFormats + StringToInt + DateToString

  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3*1024*1024)))

  before() {
    if (requireLogin){
      authenticate()
      if(isAnonymous) halt(SC_UNAUTHORIZED)
    }
    contentType = formats("json")
    logger.info(requestToString)
  }

  private def requestToString = {
    var message: String = "%s url=%s, context=%s, uri=%s, path=%s"
      .format(request.getMethod, request.getRequestURL, request.getContextPath, request.getRequestURI, request.getPathInfo)
    val names = JavaConversions.enumerationAsScalaIterator(request.getParameterNames)
    if (names.nonEmpty) {
      message += ", parameters="
      for (name <- names) {
        message += name + "::" + params(name) + " "
      }
    }
    message
  }
}
