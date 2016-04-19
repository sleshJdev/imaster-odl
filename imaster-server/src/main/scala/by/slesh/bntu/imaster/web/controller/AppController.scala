package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{User, UserRepository}
import by.slesh.bntu.imaster.security.{AuthenticationSupport, ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class AppController extends AbstractController {
  val logger = LoggerFactory.getLogger(getClass)
  val userRepository = new UserRepository

  get("/?") {
    logger.info("open home page")
  }

  post("/login") {
    val username = params("username")
    var password = params("password")
    logger.info("login with username: {} and password: {}", username, password, "")
    val userOption = Await.result(userRepository.getUserByName(username), 60 second)
    logger.info("user option: {}", userOption)
    userOption match {
      case Some(x) =>
        if (x.password == password) {
          val claims = getClaimsForUser(x)
          TokenService.createToken(claims)
        } else ""
      case None => ""
    }
  }

  def getClaimsForUser(user: User) = Map(
    ClaimsKeys.ID -> user.id.toString,
    ClaimsKeys.NAME -> user.username,
    ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
  )
}
