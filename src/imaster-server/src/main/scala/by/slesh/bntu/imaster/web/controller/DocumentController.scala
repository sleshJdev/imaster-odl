package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Document
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class DocumentController extends AbstractController{
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?") {
    logger.debug("getting all documents")
    Document.getAll
  }
}
