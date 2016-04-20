package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.RoleRepository
import org.scalatest._

/**
  * @author slesh
  */
class RoleRepositoryTests extends FunSpec with BeforeAndAfterAll {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  override protected def beforeAll(): Unit = DatabaseConnector.initialize()
  override protected def afterAll(): Unit = DatabaseConnector.release()

  val roleRepository = new RoleRepository

  describe("Role repository") {
    it("should returns role by valid id") {
      val roleId = 1
      roleRepository.getRoleById(1) onComplete { r =>
        val role = r.getOrElse(fail("can't find role with id=%s" format roleId))
        role match {
          case Some(x) => assertResult(roleId)(x.id)
          case None => fail("role with id %d not found" format roleId)
        }
      }
    }

    it("should returns role by valid name") {
      val roleName = "student"
      roleRepository.getRoleByName(roleName) onComplete { r =>
        val role = r.getOrElse(fail("can't find role with id=%s" format roleName))
        role match {
          case Some(x) => assertResult(roleName)(x.name)
          case None => fail("role with id %d not found" format roleName)
        }
      }
    }
  }
}
