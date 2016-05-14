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
  val studentRepository = new StudentRepository

  "A StudentRepository " should "returns not empty student list" in {
    studentRepository.getAll onComplete {
      case Success(list) => list should not be empty
      case Failure(ex) => fail(ex)
    }
  }

  it should "returns valid student with given id" in {
    val studentId = 1
    studentRepository.getById(studentId) onComplete {
      case Success(Some(student)) => student.id.get shouldEqual studentId
      case Failure(ex) => fail(ex)
      case _ => fail()
    }
  }

  it should "insert student and returns his id " in {
    val student = Student(None, "test", "test", Some("test"), new Date(currentTimeMillis()))
    studentRepository.add(student) onComplete {
      case Success(id) => id should be > 0
      case Failure(ex) => fail(ex)
    }
  }
}
