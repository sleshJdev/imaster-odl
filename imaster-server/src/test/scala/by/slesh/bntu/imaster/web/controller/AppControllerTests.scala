package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.DatabaseConnector
import org.scalatest._
import org.scalatra.test.scalatest.ScalatraSuite
import org.slf4j.LoggerFactory

/**
 * @author yauheni.putsykovich
 */
class AppControllerTests extends ScalatraSuite with FlatSpecLike {
  override protected def beforeAll(): Unit = {
    DatabaseConnector.initialize()
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    DatabaseConnector.release()
    super.afterAll()
  }

  addServlet(new AppController, "/*")

  "A AppController" should "for GET '/' path return status 401" in {
    get("/") {
      status should equal(401)
      body shouldBe empty
    }
  }

  it should "for GET '/nofound' path return 404 status" in {
    val username = "student"
    val password = "studentp"
    post("/nofound") {
      status should equal(404)
    }
  }

  it should "return auth token for existing username/password" in {
    val username = "student"
    val password = "studentp"
    post("/login", Map("username" -> username, "password" -> password)) {
      status should equal(200)
      body should not be null
    }
  }
}
