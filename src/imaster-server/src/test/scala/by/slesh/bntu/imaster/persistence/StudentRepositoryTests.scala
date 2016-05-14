package by.slesh.bntu.imaster.persistence

import java.lang.System.currentTimeMillis
import java.sql.Date

import by.slesh.bntu.imaster.persistence.Models.Student
import by.slesh.bntu.imaster.persistence.Repositories.StudentRepository

import scala.util.{Failure, Success}

/**
  * @author slesh
  */
class StudentRepositoryTests extends TestConfig {
  val repository = new StudentRepository

  "getStudentById method" should "returns valid Student instance with given id" in {
    val studentId = 1
    repository.getById(studentId) onComplete {
      case Success(Some(student)) => student.id.get shouldEqual studentId
      case Failure(ex) => fail(ex)
      case _ => fail()
    }
  }

  "addStudent method" should "insert Student into table" in {
    val student = Student(None, "test", "test", Some("test"), new Date(currentTimeMillis()))
    repository.add(student) onComplete {
      case Success(id) => id should be > 0
      case Failure(ex) => fail(ex)
    }
  }
}
