package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.User
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class TeacherController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?") {
    User.getAll map {
      _.filter(_.roles.exists(_.name == "teacher"))
    }
  }
}
