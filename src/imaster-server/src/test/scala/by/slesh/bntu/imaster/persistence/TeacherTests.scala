package by.slesh.bntu.imaster.persistence

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * @author slesh
  */
class TeacherTests extends TestConfig{
  "sss" should "" in {
    val result = Await.result(Teacher.getAll, Duration("10 second"))
    result.foreach { i =>
      println("%s %d %d" format(i.firstName))
    }
  }
}
