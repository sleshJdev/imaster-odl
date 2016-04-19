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

  private val LOGGER = LoggerFactory.getLogger(getClass)

  addServlet(new AppController, "/*")

  "A AppController" should "for GET '/' path return status 200" in {
    get("/") {
      status should equal(200)
    }
  }
  it should "return auth token for existing username/password" in {
    val username = "student"
    val password = "studentp"
    post("/login", Map("username" -> username, "password" -> password), headers = Map("X-Auth" -> "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyIiwidXNlcm5hbWUiOiJzdHVkZW50IiwiY3JlYXRlZFRpbWUiOiIxNDYxMTA4MzQ3MzIzIn0.Wcnpj734jh1f3nKRBVoL-dqKIHePfDb7YrffDbAO8Br5kx6wKxysk0f2ta03XdVN")) {
      status should equal(200)
      body should not be empty
    }
  }
}
