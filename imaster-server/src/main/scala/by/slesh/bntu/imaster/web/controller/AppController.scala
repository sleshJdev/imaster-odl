package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{User, UserRepository}
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory

import scala.language.postfixOps

class AppController extends AbstractController {
  val logger = LoggerFactory.getLogger(getClass)
  val repository = new UserRepository

  get("/?") {
    authenticate()
    logger.info("open home page")
  }

  post("/login") {
    val username = params("username")
    val password = params("password")
    logger.info("login with username: {} and password: {}", username, password, "")
    repository.getUserByName(username) onComplete { u =>
      val userOption = u.getOrElse(halt(401))
      logger.info("user option: {}", userOption)
      userOption match {
        case Some(x) =>
          if (x.password == password) {
            val claims = getClaimsForUser(x)
            val token = TokenService.createToken(claims)
            logger.info("created authentication token: {}", token)
            token
          } else halt(401)
        case None => halt(401)
      }
    }
  }

  def getClaimsForUser(user: User) = Map(
    ClaimsKeys.ID -> user.id.toString,
    ClaimsKeys.NAME -> user.username,
    ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
  )
}
