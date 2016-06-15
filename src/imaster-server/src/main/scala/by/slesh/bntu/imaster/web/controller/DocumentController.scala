package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{UserEssay, Essay, Document}
import by.slesh.bntu.imaster.util.FileService
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

import scala.concurrent.Future

/**
  * @author slesh
  */
class DocumentController extends AbstractController{
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?")(Document.getAll)

  get("/:id"){
    val id = params("id").toInt
    Document.getById(id)
  }

  post("/?"){
    logger.debug("creating a new document")
    val document = (parsedBody merge parse("""{"fileId": ""}""")).extract[Document]
    val fileItem = fileParams("file")
    document.fileId = fileItem.getName + "_" + fileItem.getName.hashCode
    fileItem.write(FileService.create(document.fileId))
//    Document.add(document) map { documentId =>
//      val userId = userDetails.id
//      TeacherD.add(userId, documentId)
//      Future(("userId" -> userId) ~ ("essayId" -> documentId))
//    }
  }
}
