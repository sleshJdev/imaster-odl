package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.Repositories.StudentRepository
import org.scalatest._
import scala.util.Success
import scala.util.Failure

/**
  * @author slesh
  */
class StudentRepositoryTests extends FlatSpec with BeforeAndAfterAll with Matchers {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  override protected def beforeAll(): Unit = DatabaseConnector.initialize()
  override protected def afterAll(): Unit = DatabaseConnector.release()

  val repository = new StudentRepository

  "getStudentById" should "return valid Student instance with given id" in {
    val studentId = 1
    repository.getStudentById(studentId) onComplete {
      case Success(Some(student)) =>
        student.id.get shouldEqual studentId
      case Failure(ex) => fail(ex)
    }
  }
}
