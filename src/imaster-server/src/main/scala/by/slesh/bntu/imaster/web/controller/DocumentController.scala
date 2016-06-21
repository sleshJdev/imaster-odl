package by.slesh.bntu.imaster.web.controller

import java.util.Date

import by.slesh.bntu.imaster.persistence.{StudentEssay$, Essay, Document}
import by.slesh.bntu.imaster.util.FileService
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory
import org.json4s.JsonDSL._

import scala.concurrent.Future

/**
  * @author slesh
  */
class DocumentController extends AbstractController{
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?")(Document.getAll)

  get("/:id")(Document.getById(params("id").toInt))

  post("/?"){
    logger.debug("creating a new document")
    val document = parse(params("data")).extract[Document]
    val fileItem = fileParams("file")
    document.loadedDate = Some(new java.sql.Date(System.currentTimeMillis()))
    document.fileId = Option(fileItem.getName + "_" + fileItem.getName.hashCode)
    document.loadedBy = Some(userDetails.id)
    fileItem.write(FileService.create(document.fileId.get))
    Document.add(document)
  }

  delete("/:id")(Document.remove(params("id").toInt))
}
