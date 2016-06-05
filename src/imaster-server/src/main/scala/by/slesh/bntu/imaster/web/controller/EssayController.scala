package by.slesh.bntu.imaster.web.controller

import java.nio.file.Paths

import by.slesh.bntu.imaster.persistence.{Essay, StudentEssay}
import by.slesh.bntu.imaster.web.AbstractController
import org.joda.time.LocalDateTime
import org.json4s.JsonDSL._
import org.scalatra.servlet.FileUploadSupport
import org.slf4j.LoggerFactory

import scala.concurrent.Future

/**
  * @author slesh
  */
class EssayController extends AbstractController with FileUploadSupport {

  override val logger = LoggerFactory.getLogger(getClass)

  get("/?") {
    logger.debug("getting all essays")
    Essay.getAll
  }

  get("/:id") {
    val id = params("id").toInt
    logger.debug("getting essay by id {}", id)
    Essay.getById(id)
  }

  post("/?") {
    logger.debug("creating a new essay")
    val essay = (parse(params("data")) merge parse("""{"fileId": ""}""")).extract[Essay]
    val fileItem = fileParams("file")
    essay.fileId = LocalDateTime.now + "_" + fileItem.getName
    fileItem.write(Paths.get("/home/slesh/trash/", essay.fileId).toFile)
    Essay.add(essay) map { essayId =>
      val userId = userDetails.id
      StudentEssay.add(userId, essayId)
      Future(("userId" -> userId) ~ ("essayId" -> essayId))
    }
  }
}
