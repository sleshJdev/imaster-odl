package by.slesh.bntu.imaster.web.controller

import javax.servlet.http.HttpServletResponse

import by.slesh.bntu.imaster.persistence.Models._
import by.slesh.bntu.imaster.persistence.Repositories._
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import by.slesh.bntu.imaster.web.model.Account
import org.json4s.JsonDSL._
import org.json4s._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.language.postfixOps

class AppController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)
  val repository = new UserRepository

  get("/?") {
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
        if (token.isEmpty) response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        pretty(("token" -> token.orNull) ~ ("username" -> account.username))
      }
    }
  }

  def getClaimsForUser(user: User) = user match {
    case User(Some(id), username, _, _, _, _) =>
      Map(
        ClaimsKeys.ID -> id.toString,
        ClaimsKeys.NAME -> username,
        ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
      )
    case _ => halt(401, "user %s not found" format user)
  }
}
