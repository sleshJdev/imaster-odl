package by.slesh.bntu.imaster.persistence

import scala.util.{Failure, Success}

/**
  * @author slesh
  */
class RoleRepositoryTests extends TestConfig {
  "A RoleRepository" should "returns not empty role list" in {
    Role.getAll onComplete {
      case Success(x) => x should not be empty
      case Failure(ex) => fail(ex)
    }
  }

  it should "returns role by valid id" in {
    val roleId = 1
    Role.getById(roleId) onComplete {
      case Success(Some(x)) => assertResult(roleId)(x.id.get)
      case Failure(ex) => fail(ex)
      case _ => fail("role with id %d not found" format roleId)
    }
  }

  it should "returns role by valid name" in {
    val roleName = "student"
    Role.getRoleByName(roleName) onComplete {
      case Success(Some(x)) => assertResult(roleName)(x.name)
      case Failure(ex) => fail(ex)
      case _ => fail("role with id %s not found" format roleName)
    }
  }
}
