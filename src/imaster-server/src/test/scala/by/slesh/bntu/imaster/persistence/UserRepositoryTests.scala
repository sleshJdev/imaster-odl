package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Models.UserExtended
import by.slesh.bntu.imaster.persistence.Repositories._
import org.scalatest._

/**
  * @author yauheni.putsykovich
  */
class UserRepositoryTests extends FlatSpec with BeforeAndAfterAll with Matchers {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  override protected def beforeAll(): Unit = DatabaseConnector.initialize()
  override protected def afterAll(): Unit = DatabaseConnector.release()

  val repository = new UserRepository

  "getUserById method" should "returns User instance by existing id" in {
    val userId = 1
    repository getUserById userId onComplete { u =>
      val user = u.getOrElse(fail("can't find user with id=%s" format userId))
      user match {
        case Some(UserExtended(u, rs)) =>
          assertResult(userId)(u.id.get)
          rs should have length(2)
        case _ => fail("user with id %d not found" format userId)
      }
    }
  }

  "getUserByName method" should "returns valid User instance by username" in {
    val username = "student"
    repository getUserByName username onComplete { u =>
      val student = u.getOrElse(fail("can't find user with username %s" format username))
      student match {
        case Some(x) => assertResult(username)(x.username)
        case _ => fail("user with name %s not found" format username)
      }
    }
  }

  "getAll method" should "returns all users with appropriated roles" in {
    val future = repository.getAllUsers onComplete { tryList =>
      val list = tryList.getOrElse(fail("user list getting fails"))
      list should not be empty
    }
  }
}
