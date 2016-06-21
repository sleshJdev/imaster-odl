package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

/**
  * @author slesh
  */

case class StudentEssay(studentId: Int,
                        essayId: Int)

class StudentEssays(tag: Tag) extends Table[StudentEssay](tag, "student_essay") {
  def userId = column[Int]("user_id")
  def essayId = column[Int]("essay_id")

  def user = foreignKey("fk_user__essay__student_id__student_id", userId, User.models)(_.id)
  def essay = foreignKey("fk_user__essay__essay_id__essay_id", essayId, Essay.models)(_.id)

  def id = primaryKey("pk_user_x_essay", (userId, essayId))

  override def *  = (userId, essayId) <> ((StudentEssay.apply _).tupled, StudentEssay.unapply)
}

object StudentEssay extends Repositorie {
  var models = TableQuery(new StudentEssays(_))

  def getByStudentId(studentId: Int) = {
    db.run(models.filter(_.userId === studentId).result.headOption)
  }

  def add(studentId: Int, essayId: Int): Future[Int] = {
    db.run(models.insertOrUpdate(StudentEssay(studentId, essayId))).map(_.toInt)
  }
}

