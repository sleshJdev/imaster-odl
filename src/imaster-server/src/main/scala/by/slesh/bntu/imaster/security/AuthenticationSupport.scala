package by.slesh.bntu.imaster.security

import javax.servlet.http.HttpServletRequest

import by.slesh.bntu.imaster.persistence.{User, UserExtended}
import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

/**
 * @author slesh
 */
trait AuthenticationSupport extends ScalatraBase with ScentrySupport[UserDetails] {
  self: ScalatraBase =>

  val logger = LoggerFactory.getLogger(getClass)
  val tokenBasedStrategy = new TokenBasedStrategy(self)
  val userRepository = User

  override protected def scentryConfig: ScentryConfiguration = new ScentryConfig {
    override val login: String = "/login"
  }.asInstanceOf[ScentryConfiguration]

  protected def requireLogin(implicit request: HttpServletRequest) = {
    !Option(request.getPathInfo).getOrElse("").contains(scentryConfig.login)
  }

  protected def userDetails(implicit request: HttpServletRequest): UserDetails = {
    val token = request.getHeader(tokenBasedStrategy.TOKEN_AUTH_NAME)
    TokenService.parseToken(token) match {
      case Some(claims) => fromSession.lift(claims(ClaimsKeys.NAME)).get
      case _ => throw new UserNotFoundException("token %s not found" format tokenBasedStrategy.TOKEN_AUTH_NAME)
    }
  }

  /**
   * If an unauthenticated user attempts to access a route which is protected by Scentry,
   * run the unauthenticated() method on the UserPasswordStrategy.
   */
  override protected def configureScentry = {
    scentry.unauthenticated {
      scentry.strategies(tokenBasedStrategy.name).unauthenticated()
    }
  }

  /**
   * Register auth strategies with Scentry. Any controller with this trait mixed in will attempt to
   * progressively use all registered strategies to log the user in, falling back if necessary.
   */
  override protected def registerAuthStrategies = {
    scentry.register(tokenBasedStrategy.name, app => tokenBasedStrategy)
  }

  /*
  * TODO: i think need provide some stub to these methods, because they will be not used??????
  * */

  override protected def fromSession: PartialFunction[String, UserDetails] = {
    case username: String =>
      Await.result(userRepository.getUserByName(username), 60 second) match {
        case Some(UserExtended(user, roles)) =>
          logger.info("from session {} -> user: {}, roles: {}", username, user, roles)
          UserDetails(user.id.get, user.username, user.password)
        case None => throw new UserNotFoundException("user with name %s not found" format username)
      }
  }

  override protected def toSession: PartialFunction[UserDetails, String] = {
    case user: UserDetails =>
      logger.info("to session {} -> {}", user, user.username, "")
      user.username
  }
}


