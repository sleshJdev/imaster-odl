package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Role.{USER, getAllRoles}
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class RolesController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/public/?")(getAllRoles map (_.filterNot(_.name == USER)))
}
