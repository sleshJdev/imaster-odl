package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Models.UserExtended
import by.slesh.bntu.imaster.persistence.Repositories._

import scala.util.{Failure, Success}

/**
  * @author yauheni.putsykovich
  */
class UserRepositoryTests extends TestConfig {
  val repository = new UserRepository

  "getUserById method" should "returns User instance by existing id" in {
    val userId = 1
    repository getById userId onComplete {
      case Success(Some(UserExtended(user, roles))) =>
        assertResult(userId)(user.id.get)
        roles should not be empty
      case Failure(ex) => fail(ex)
      case _ => fail("user with id %d not found" format userId)
    }
  }

  "getUserByName method" should "returns valid User instance by username" in {
    val username = "student"
    repository getUserByName username onComplete {
      case Success(Some(x)) => assertResult(username)(x.user.username)
      case Failure(ex) => fail(ex)
      case _ => fail("user with name %s not found" format username)
    }
  }

  "getAll method" should "returns all users with appropriated roles" in {
    repository.getAll onComplete {
      case Success(list) => list should not be empty
      case Failure(ex) => fail(ex)
    }
  }
}
