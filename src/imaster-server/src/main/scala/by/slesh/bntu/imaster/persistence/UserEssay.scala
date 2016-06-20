package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

/**
  * @author slesh
  */

case class UserEssay(studentId: Int,
                     essayId: Int)

class UserEssays(tag: Tag) extends Table[UserEssay](tag, "student_essay") {
  def userId = column[Int]("user_id")
  def essayId = column[Int]("essay_id")

  def user = foreignKey("fk_user__essay__student_id__student_id", userId, User.models)(_.id)
  def essay = foreignKey("fk_user__essay__essay_id__essay_id", essayId, Essay.models)(_.id)

  def id = primaryKey("pk_user_x_essay", (userId, essayId))

  override def *  = (userId, essayId) <> ((UserEssay.apply _).tupled, UserEssay.unapply)
}

object UserEssay extends Repositorie {
  var models = TableQuery(new UserEssays(_))

  def getByStudentId(studentId: Int) = {
    db.run(models.filter(_.userId === studentId).result.headOption)
  }

  def add(studentId: Int, essayId: Int): Future[Int] = {
    db.run(models.insertOrUpdate(UserEssay(studentId, essayId))).map(_.toInt)
  }
}

