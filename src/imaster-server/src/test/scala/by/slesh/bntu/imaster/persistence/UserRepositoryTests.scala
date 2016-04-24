package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Models.UserExtended
import by.slesh.bntu.imaster.persistence.Repositories._
import org.scalatest._
import scala.util.Success
import scala.util.Failure

/**
  * @author yauheni.putsykovich
  */
class UserRepositoryTests extends TestConfig {
  val repository = new UserRepository

  "getUserById method" should "returns User instance by existing id" in {
    val userId = 1
    repository getUserById userId onComplete { u =>
      val user = u.getOrElse(fail("can't find user with id=%s" format userId))
      user match {
        case Some(UserExtended(user, rs)) =>
          assertResult(userId)(user.id.get)
          rs should have length 2
        case _ => fail("user with id %d not found" format userId)
      }
    }
  }

  "getUserByName method" should "returns valid User instance by username" in {
    val username = "student"
    repository getUserByName username onComplete {
      case Success(Some(x)) => assertResult(username)(x.username)
      case Failure(ex) => fail(ex)
      case _ => fail("user with name %s not found" format username)
    }
  }

  "getAll method" should "returns all users with appropriated roles" in {
    repository.getAllUsers onComplete {
      case Success(list) => list should not be empty
      case Failure(ex) => fail(ex)
    }
  }
}
