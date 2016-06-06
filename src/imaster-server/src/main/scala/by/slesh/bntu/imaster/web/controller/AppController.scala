package by.slesh.bntu.imaster.web.controller

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

import by.slesh.bntu.imaster.persistence.{Student, Teacher, Role, User}
import by.slesh.bntu.imaster.security.{ClaimsKeys, TokenService}
import by.slesh.bntu.imaster.web.AbstractController
import by.slesh.bntu.imaster.web.model.{Account, AuthData}
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.language.postfixOps
import scala.util.Success

class AppController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?") {
    logger.info("open home page")
  }

  post("/login") {
    val account = parsedBody.extract[Account]
    logger.info("{} is login...", account)
    User.getUserByName(account.username) map { user =>
      logger.info("found account: {}", user)
      getAuthData(user.get, account, getToken(user, account.password))
    }
  }

  def resolveRole(userId: Int, loginAs: String) = {
    loginAs match {
      case "teacher" => Teacher.getByUserId(userId)
      case "student" => Student.getByUserId(userId)
      case _ => Future(None)
    }
  }

  def getAuthData(user: User, account: Account, token: Option[String]): Future[Option[AuthData]] = {
    import org.json4s.jackson.Serialization.write
    if (token.isEmpty) Future {
      response.setStatus(SC_UNAUTHORIZED)
      None
    } else resolveRole(user.id.get, account.loginAs) map { info =>
      Some(AuthData(token.orNull, user.username, user.roles.map(_.name), Option(write(info.orNull))))
    }
  }

  def getToken(user: Option[User], password: String) = user match {
    case Some(x) =>
      if (x.password == password) {
        val token = TokenService.createToken(getClaimsForUser(x))
        logger.info("created authentication token: {}", token)
        Some(token)
      } else None
    case None => None
  }

  def getClaimsForUser(user: User) = user match {
    case User(Some(id), username, _, _) =>
      Map(
        ClaimsKeys.ID -> id.toString,
        ClaimsKeys.NAME -> username,
        ClaimsKeys.CREATED_TIME -> System.currentTimeMillis().toString
      )
    case _ => halt(401, "user %s not found" format user)
  }
}
