package by.slesh.bntu.imaster.web.controller

import org.scalatest._
import org.scalatra.test.scalatest.ScalatraSuite

/**
 * @author yauheni.putsykovich
 */
class AppControllerTests extends ScalatraSuite with FlatSpecLike {
  addServlet(new AppController, "/*")
  "A AppController for GET '/' path" should "return status 200" in {
    get("/") {
      status should equal(200)
    }
  }
}
