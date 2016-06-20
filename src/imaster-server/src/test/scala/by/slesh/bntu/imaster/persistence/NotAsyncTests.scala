package by.slesh.bntu.imaster.persistence

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._


/**
  * @author slesh
  */
class NotAsyncTests extends TestConfig {
  "Repository" should "get not empty collection" in {
//    val result = Await.result(db.run(query.result), Duration("20 second"))
//    result.foreach(println)
  }
}
