package by.slesh.bntu.imaster.security

import java.util.concurrent.TimeUnit
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import by.slesh.bntu.imaster.persistence.UserRepository
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}
import scala.language.postfixOps

/**
  * @author slesh
  */
class TokenBasedStrategy(protected val app: ScalatraBase)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[UserDetails] {

  private val TOKEN_AUTH_NAME = "X-Auth"

  private val logger = LoggerFactory.getLogger(getClass)
  private val userRepository = new UserRepository

  var tokenLifeTime = Duration(10L, TimeUnit.MINUTES).toMillis

  override def name: String = getClass.getSimpleName

  override def isValid(implicit request: HttpServletRequest): Boolean = {
    val isOk = Option(request.getHeader(TOKEN_AUTH_NAME)).isDefined
    if (!isOk) logger.info("header '{}' not present in request", TOKEN_AUTH_NAME)
    isOk
  }

  override def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[UserDetails] = {
    val tokenJson = request.getHeader(TOKEN_AUTH_NAME)
    logger.info("authenticate with token: {}", tokenJson)
    TokenService.parseToken(tokenJson) match {
      case None => None
      case Some(claims) => validateToken(tokenJson, claims)
    }
  }

  def validateToken(tokenJson: String, providedClaims: Map[String, String]): Option[UserDetails] = {
    logger.info("validateToken with providedClaims: {}", providedClaims)
    if (TokenService.validate(tokenJson)) throw new InvalidTokenException("invalid token")

    val createdTime = providedClaims.get(ClaimsKeys.CREATED_TIME).get.toLong
    logger.info("validate token life time: %d from %d", createdTime, tokenLifeTime)
    if (System.currentTimeMillis() - createdTime > tokenLifeTime) throw new InvalidTokenException("token is expired")

    logger.info("loading user from database...")
    val id = providedClaims.get(ClaimsKeys.ID).get.toInt
    val user = Await.result(userRepository.getUserById(id), 60 second) match {
      case Some(u) => u
      case None => throw new InvalidTokenException("invalid user information")
    }

    var userDetails = Some(UserDetails(user.username, user.password))
    logger.info("created user details: {}", userDetails)
    userDetails
  }
}
