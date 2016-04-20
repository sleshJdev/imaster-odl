package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{User, UserRepository}
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class AppController extends AbstractController {
  val logger = LoggerFactory.getLogger(getClass)
  val userRepository = new UserRepository

  get("/?") {
    authenticate()
    logger.info("open home page")
  }

  post("/login") {
    val username = params("username")
    val password = params("password")
    logger.info("login with username: {} and password: {}", username, password, "")
    val userOption = Await.result(userRepository.getUserByName(username), 10 second)
    logger.info("user option: {}", userOption)
    userOption match {
      case Some(x) =>
        if (x.password == password) {
          val claims = getClaimsForUser(x)
          val token = TokenService.createToken(claims)
          logger.info("created authentication token: {}", token)
          token
        } else halt(status = 401)
      case None => halt(status = 401)
    }
  }

  def getClaimsForUser(user: User) = Map(
    ClaimsKeys.ID -> user.id.toString,
    ClaimsKeys.NAME -> user.username,
    ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
  )
}
