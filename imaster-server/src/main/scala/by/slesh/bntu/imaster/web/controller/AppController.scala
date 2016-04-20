package by.slesh.bntu.imaster.web.controller

import javax.servlet.http.HttpServletResponse

import by.slesh.bntu.imaster.persistence.{User, UserRepository}
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import by.slesh.bntu.imaster.web.model.Account
import org.json4s.JsonDSL._
import org.json4s._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.language.postfixOps

class AppController extends AbstractController {
  val logger = LoggerFactory.getLogger(getClass)
  val repository = new UserRepository

  get("/?") {
    authenticate()
    logger.info("open home page")
  }

  post("/login") {
    val account = parsedBody.extract[Account]
    logger.info("{} is login...", account)
    repository.getUserByName(account.username) flatMap { userOption =>
      logger.info("found account: {}", userOption)
      val token = userOption match {
        case Some(user) =>
          if (user.password == account.password) {
            val token = TokenService.createToken(getClaimsForUser(user))
            logger.info("created authentication token: {}", token)
            Some(token)
          } else None
        case None => None
      }
      Future {
        if(token.isEmpty) response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        pretty("token" -> token.orNull)
      }
    }
  }

  def getClaimsForUser(user: User) = Map(
    ClaimsKeys.ID -> user.id.toString,
    ClaimsKeys.NAME -> user.username,
    ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
  )
}
