package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
  * @author slesh
  */
case class TeacherEssay(id: Int,
                          teacherId: Int,
                          essayId: Int)

class TeachersEssays(tag: Tag) extends Table[TeacherEssay](tag, "teacher_essay"){
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def teacherId = column[Int]("teacher_id")
  def essayId = column[Int]("essay_id")

  def teacher = foreignKey("fk__teacher_essay__teacher_id__teacher_id", teacherId, Teacher.models)(_.id)
  def essay = foreignKey("fk__teacher_essay__essay_id__essay_id", essayId, Essay.models)(_.id)

  override def * = (id, teacherId, essayId) <>((TeacherEssay.apply _).tupled, TeacherEssay.unapply)
}


object TeacherEssay{
  val models = TableQuery(new TeachersEssays(_))
}