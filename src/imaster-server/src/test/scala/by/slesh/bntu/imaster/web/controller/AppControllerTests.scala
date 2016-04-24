package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.DatabaseConnector
import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest._
import org.scalatra.test.scalatest.ScalatraSuite

/**
 * @author yauheni.putsykovich
 */
class AppControllerTests extends ScalatraSuite with FlatSpecLike {
  import org.json4s.DefaultFormats
  implicit val formats = DefaultFormats

  override protected def beforeAll(): Unit = {
    DatabaseConnector.initialize()
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    DatabaseConnector.release()
    super.afterAll()
  }

  addServlet(new AppController, "/api/*")

  "A AppController" should "for GET '/api' path returns status 401" in {
    get("/api") {
      status should equal(401)
      body shouldBe empty
    }
  }

  it should "for GET '/api/nofound' path returns 404 status" in {
    post("/api/nofound") {
      status should equal(404)
    }
  }

  it should "for POST '/api/login' returns auth token for existing username/password" in {
    post("/api/login", pretty(("username" -> "student") ~ ("password" -> "studentp"))) {
      status should equal(200)
      response.getContentType() should include("application/json")
      val bodyJson = parse(body)
      (bodyJson \ "token").extract[String] should not be empty
    }
  }
}
