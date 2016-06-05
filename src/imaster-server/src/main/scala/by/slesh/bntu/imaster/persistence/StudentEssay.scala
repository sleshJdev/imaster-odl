package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

/**
  * @author slesh
  */

case class StudentEssay(id: Option[Int],
                        studentId: Int,
                        essayId: Int)

class StudentEssays(tag: Tag) extends Table[StudentEssay](tag, "student_essay") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def studentId = column[Int]("student_id")

  def essayId = column[Int]("essay_id")

  override def *  = (id.?, studentId, essayId) <> ((StudentEssay.apply _).tupled, StudentEssay.unapply)
}

object StudentEssay extends Repositorie {
  var models = TableQuery(new StudentEssays(_))

  def add(studentId: Int, essayId: Int): Future[Int] = {
    db.run(models.insertOrUpdate(StudentEssay(None, studentId, essayId))).map(_.toInt)
  }
}

