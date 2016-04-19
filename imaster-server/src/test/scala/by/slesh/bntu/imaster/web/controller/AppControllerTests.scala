package by.slesh.bntu.imaster.web.controller

import org.scalatest._
import org.scalatra.test.scalatest.ScalatraSuite
import org.slf4j.LoggerFactory

/**
 * @author yauheni.putsykovich
 */
class AppControllerTests extends ScalatraSuite with FlatSpecLike {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  addServlet(new AppController, "/*")

  "A AppController" should "for GET '/' path return status 200" in {
    get("/") {
      status should equal(200)
    }
  }
  it should "return auth token for existing login/password" in {
    val username = "student"
    val password = "student"
    post("/login", "username" -> username, "password" -> password){
      status should equal(200)
      body should equal("auth-token")
    }
  }
}
