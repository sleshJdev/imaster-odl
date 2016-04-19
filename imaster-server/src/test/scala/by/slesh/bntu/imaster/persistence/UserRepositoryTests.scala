package by.slesh.bntu.imaster.persistence

import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.slf4j.LoggerFactory

/**
  * @author yauheni.putsykovich
  */
class UserRepositoryTests extends FlatSpec with BeforeAndAfterAll {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  val repository = new UserRepository

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  "A UserRepository" should "return User instance by existing id" in {
    val userId = 1
    repository getUserById userId onComplete { u =>
      val user = u.getOrElse(fail("can't find user with id=%s" format userId))
      user match {
        case Some(x) => assertResult(userId)(x.id)
        case None => fail("user with id %d not found" format userId)
      }
    }
  }

  it should "return valid User instance by username" in {
    val username = "student"
    repository getUserByName username onComplete { u =>
      val student = u.getOrElse(fail("can't find user with username %s" format username))
      student match {
        case Some(x) => assertResult(username)(x.username)
        case None => fail("user with name %s not found" format username)
      }
    }
  }

  override protected def beforeAll(): Unit = DatabaseConnector.initialize()

  override protected def afterAll(): Unit = DatabaseConnector.release()
}
