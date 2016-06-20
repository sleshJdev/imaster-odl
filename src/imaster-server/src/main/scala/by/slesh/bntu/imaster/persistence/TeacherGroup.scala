package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
  * @author slesh
  */
case class TeacherGroup(teacherId: Int,
                        groupId: Int)

class TeachersGroups(tag: Tag) extends Table[TeacherGroup](tag, "teacher_group") {
  def teacherId = column[Int]("teacher_id")
  def groupId = column[Int]("group_id")

  def teacher = foreignKey("fk__teacher_group__teacher_id__teacher_id", teacherId, Teacher.models)(_.id)
  def group = foreignKey("fk__teacher_group__group_id__group_id", groupId, Group.models)(_.id)

  def id = primaryKey("pk_teacher_x_group", (teacherId, groupId))

  override def * = (teacherId, groupId) <>((TeacherGroup.apply _).tupled, TeacherGroup.unapply)
}

object TeacherGroup {
  val models = TableQuery(new TeachersGroups(_))
}