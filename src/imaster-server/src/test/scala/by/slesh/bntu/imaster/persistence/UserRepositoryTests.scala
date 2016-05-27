package by.slesh.bntu.imaster.persistence

import scala.util.{Failure, Success}

/**
  * @author yauheni.putsykovich
  */
class UserRepositoryTests extends TestConfig {
  "A UserRepository" should "returns all users with appropriated roles" in {
    User.getAll onComplete {
      case Success(list) =>
        list should not be empty
        forAll(list) { user => user.roles should not be empty }
      case Failure(ex) => fail(ex)
    }
  }

  it should "returns user by existing id" in {
    val userId = 1
    User getById userId onComplete {
      case Success(Some(UserExtended(user, roles))) =>
        assertResult(userId)(user.id.get)
        roles should not be empty
      case Failure(ex) => fail(ex)
      case _ => fail("user with id %d not found" format userId)
    }
  }

  it should "returns valid user by username" in {
    val username = "student"
    User getUserByName username onComplete {
      case Success(Some(x)) => assertResult(username)(x.user.username)
      case Failure(ex) => fail(ex)
      case _ => fail("user with name %s not found" format username)
    }
  }

}
