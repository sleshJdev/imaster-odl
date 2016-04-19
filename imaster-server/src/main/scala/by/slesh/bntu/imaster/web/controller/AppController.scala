package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

class AppController extends AbstractController {
  val LOGGER = LoggerFactory.getLogger(getClass)

  get("/?") {
    LOGGER.info("open home page")
  }

  post("/login") {
    val username = params("username")
    val password = params("password")
    LOGGER.info("login with username: {} and password: {}", username, password, "")
    getToken
  }

  def getToken: String = {
    "auth-token"
  }
}
