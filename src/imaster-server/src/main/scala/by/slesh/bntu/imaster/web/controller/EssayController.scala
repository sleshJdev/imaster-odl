package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{StudentEssay, Essay}
import by.slesh.bntu.imaster.util.FileService
import by.slesh.bntu.imaster.web.AbstractController
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
    essay.fileId = fileItem.getName + "_" + fileItem.getName.hashCode
    fileItem.write(FileService.create(essay.fileId))
    Essay.add(essay) map { essayId =>
      val studentId = userDetails.id
      StudentEssay.add(studentId, essayId)
      Future(("userId" -> studentId) ~ ("essayId" -> essayId))
    }
  }

  put("/?"){
    logger.debug("updating a essay")
    val essay = parse(params("data")).extract[Essay]
    val fileItem = fileParams("file")
    fileItem.write(FileService.create(essay.fileId))
    Essay.update(essay)
  }

  delete("/:id") {
    val id = params("id").toInt
    Essay.delete(id)
  }
}
