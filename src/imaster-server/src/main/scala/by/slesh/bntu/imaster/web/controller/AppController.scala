package by.slesh.bntu.imaster.web.controller

import javax.servlet.http.HttpServletResponse

import by.slesh.bntu.imaster.persistence.User
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import by.slesh.bntu.imaster.web.model.{Account, AuthData}
import org.json4s._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.language.postfixOps

class AppController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)
  val repository = User

  get("/?") {
    logger.info("open home page")
  }

  post("/login") {
    val account = parsedBody.extract[Account]
    logger.info("{} is login...", account)
    repository.getUserByName(account.username) map { userExtended =>
      logger.info("found account: {}", userExtended)
      val token = userExtended match {
        case Some(x) =>
          if (x.user.password == account.password) {
            val token = TokenService.createToken(getClaimsForUser(x.user))
            logger.info("created authentication token: {}", token)
            Some(token)
          } else None
        case None => None
      }
      Future {
        if (token.isEmpty) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        } else {
          userExtended match {
            case Some(x) => AuthData(token.orNull, x.user.username, x.roles.map(_.name))
            case None => None
          }
        }
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
