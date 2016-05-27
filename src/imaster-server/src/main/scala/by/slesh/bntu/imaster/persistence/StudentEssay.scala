package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector._
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

/**
  * @author slesh
  */

class StudentEssays(tag: Tag) extends Table[(Option[Int], Int, Int)](tag, "student_essay") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def studentId = column[Int]("student_id")

  def essayId = column[Int]("essay_id")

  override def * : ProvenShape[(Option[Int], Int, Int)] = (id.?, studentId, essayId)
}

object StudentEssay extends Repositorie {
  var models = TableQuery(new StudentEssays(_))

  def add(studentId: Int, essayId: Int): Future[Int] = {
    db.run(models.insertOrUpdate((None, studentId, essayId))).map(_.toInt)
  }
}

