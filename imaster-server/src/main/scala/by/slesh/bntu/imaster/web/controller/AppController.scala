package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

class AppController extends AbstractController {
  val LOGGER = LoggerFactory.getLogger(getClass)

  get("/?"){
    LOGGER.info("open home page")
  }
}
