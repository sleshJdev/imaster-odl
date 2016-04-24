package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Repositories.RoleRepository
import org.scalatest._
import scala.concurrent.Await
import scala.util.Success
import scala.util.Failure
import scala.concurrent.duration.DurationInt

/**
  * @author slesh
  */
class RoleRepositoryTests extends TestConfig {
  val roleRepository = new RoleRepository

  "getRoleById method" should "returns role by valid id" in {
    val roleId = 1
    roleRepository.getRoleById(roleId) onComplete {
      case Success(Some(x)) => assertResult(roleId)(x.id.get)
      case Failure(ex) => fail(ex)
      case _ => fail("role with id %d not found" format roleId)
    }
  }

  "getRoleByName method" should "returns role by valid name" in {
    val roleName = "student"
    roleRepository.getRoleByName(roleName) onComplete {
      case Success(Some(x)) => assertResult(roleName)(x.name)
      case Failure(ex) => fail(ex)
      case _ => fail("role with id %s not found" format roleName)
    }
  }
}
