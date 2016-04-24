package by.slesh.bntu.imaster.security

import by.slesh.bntu.imaster.persistence.Repositories._
import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.slf4j.LoggerFactory

/**
 * @author slesh
 */
trait AuthenticationSupport extends ScalatraBase with ScentrySupport[UserDetails] {
  self: ScalatraBase =>

  private val logger = LoggerFactory.getLogger(getClass)
  private val tokenBasedStrategy = new TokenBasedStrategy(self)

  override protected def scentryConfig: ScentryConfiguration = (new ScentryConfig {
    override val login: String = "/login"
  }).asInstanceOf[ScentryConfiguration]

  protected def requireLogin() = {
    if (!isAuthenticated) {
      redirect(scentryConfig.login)
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
  * TODO: i think need provide some stub to these methods, because they will be not used
  * */

  override protected def fromSession: PartialFunction[String, UserDetails] = {
    case id: String => UserDetails("", "")
  }

  override protected def toSession: PartialFunction[UserDetails, String] = {
    case user: UserDetails => ""
  }
}


