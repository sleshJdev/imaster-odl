package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Models.Student
import by.slesh.bntu.imaster.persistence.Repositories.StudentRepository
import org.scalatest._
import scala.util.Success
import scala.util.Failure

/**
  * @author slesh
  */
class StudentRepositoryTests extends TestConfig{
  val repository = new StudentRepository

  "getStudentById method" should "returns valid Student instance with given id" in {
    val studentId = 1
    repository.getStudentById(studentId) onComplete {
      case Success(Some(student)) => student.id.get shouldEqual studentId
      case Failure(ex) => fail(ex)
      case _ => fail()
    }
  }

  "addStudent method" should "insert Student into table" in {
    val student = Student(None, "test", "test", Some("test"), 0)
    repository.addStudent(student) onComplete {
      case Success(id) => id should be > 0
      case Failure(ex) => fail(ex)
    }
  }
}
