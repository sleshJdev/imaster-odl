package by.slesh.bntu.imaster.persistence

import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.slf4j.LoggerFactory

/**
 * @author yauheni.putsykovich
 */
class UserRepositoryTests extends FlatSpec with BeforeAndAfterAll {
  private val LOGGER = LoggerFactory.getLogger(getClass)
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  "A UserRepository.getById" should "return User instance" in {
    val userId = 1
    val repository = new UserRepository()
    repository.getUserById(userId) onComplete { u =>
      var user = u.getOrElse(fail("can't find user with id=%s" format userId))
      user match {
        case Some(x) => assertResult(userId)(x.id)
        case None => fail("user not found")
      }
    }
  }

  override protected def beforeAll(): Unit = DatabaseConnector.initialize()

  override protected def afterAll(): Unit = DatabaseConnector.release()
}
