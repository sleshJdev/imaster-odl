package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Status.getAll
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

/**
  * @author slesh
  */
class StatusController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?")(getAll)
}
