package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Role
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class RolesController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/public/?") {
    logger.debug("get all roles")
    Role.getAll
  }
}
