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
    val query =
      Teacher.models
        .join(TeacherEssay.models).on(_.id === _.teacherId)
        .join(Essay.models).on(_._2.essayId === _.id)
        .join(TeacherGroup.models).on(_._1._1.id === _.teacherId)
        .join(Group.models).on(_._1._1._1.id === _.id)

    val result = Await.result(db.run(query.result), Duration("20 second"))
    result.foreach(println)
  }
}
