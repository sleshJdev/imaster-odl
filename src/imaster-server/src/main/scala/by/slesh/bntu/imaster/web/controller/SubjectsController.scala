package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Subject
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class SubjectsController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?")(Subject.getAllSubjects)
}
