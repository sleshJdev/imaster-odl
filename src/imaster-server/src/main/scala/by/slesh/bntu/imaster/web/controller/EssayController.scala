package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.web.AbstractController
import org.scalatra.{BadRequest, Ok}
import org.scalatra.servlet.FileUploadSupport
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class EssayController extends AbstractController with FileUploadSupport {

  override val logger = LoggerFactory.getLogger(getClass)

  post("/?") {
    logger.info("partials: " + fileParams.size)
    fileParams.get("file") match {
      case Some(file) =>
        logger.info("file name: " + file.getName)
      case None =>
        BadRequest("Hey! You forgot to select a file.")
    }
    val json = params("data")
    logger.info(json)
    fileParams.get("data") match {
      case Some(data) =>
        logger.info("data: " + data)
      case None =>
        BadRequest("data is empty")
    }
  }
}
