package by.slesh.bntu.imaster.persistence

import java.sql.Date

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */
case class Teacher(var id: Option[Int],
                   var firstName: String,
                   var lastName: String,
                   var patronymic: Option[String] = None,
                   var email: String,
                   var birthday: Date,
                   var user: Option[User] = None)

class Teachers(tag: Tag) extends Table[Teacher](tag, "teacher") {
  def id = column[Int]("id", O.PrimaryKey)
  def firstName = column[String]("firstName", O.Length(60, varying = true))
  def lastName = column[String]("lastName", O.Length(60, varying = true))
  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))
  def email = column[String]("email", O.Length(60, varying = true))
  def birthday = column[Date]("birthday")

  def user = foreignKey("fk_teacher__user_id__user_id", id, User.models)(_.id, onDelete = ForeignKeyAction.Cascade)

  type Data = (Option[Int], String, String, Option[String], String, Date)

  def toTeacher: Data => Teacher = {
    case (id, firstName, lastName, patronymic, email, birthday) =>
      Teacher(id, firstName, lastName, patronymic, email, birthday)
  }

  def fromTeacher: PartialFunction[Teacher, Option[Data]] = {
    case Teacher(id, firstName, lastName, patronymic, email, birthday, user) =>
      Option((id, firstName, lastName, patronymic, email, birthday))
  }

  override def * =
    (id.?, firstName, lastName, patronymic, email, birthday) <>
      (toTeacher, fromTeacher)
}

object Teacher extends Repositorie with TeacherExtensions {
  val models = TableQuery(new Teachers(_))

  def getByUserId(userId: Int) = db.run(models.filter(_.id === userId).result.headOption)
  def getAll = db.run(models.result)
  def getAllPublicTeachers = db.run(models.result)
}


trait TeacherExtensions {


}