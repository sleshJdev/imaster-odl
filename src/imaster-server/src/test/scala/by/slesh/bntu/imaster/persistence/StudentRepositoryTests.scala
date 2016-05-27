package by.slesh.bntu.imaster.persistence

import java.lang.System.currentTimeMillis
import java.sql.Date

import scala.util.{Failure, Success}

/**
  * @author slesh
  */
class StudentRepositoryTests extends TestConfig {
  "A StudentRepository " should "returns not empty student list" in {
    Student.getAll onComplete {
      case Success(list) => list should not be empty
      case Failure(ex) => fail(ex)
    }
  }

  it should "returns valid student with given id" in {
    val studentId = 1
    Student.getById(studentId) onComplete {
      case Success(Some(student)) => student.id.get shouldEqual studentId
      case Failure(ex) => fail(ex)
      case _ => fail()
    }
  }

  it should "insert student and returns his id " in {
    val student = Student(None, "test", "test", Some("test"), new Date(currentTimeMillis()))
    Student.add(student) onComplete {
      case Success(id) => id should be > 0
      case Failure(ex) => fail(ex)
    }
  }
}
