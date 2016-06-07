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
                   var user: Option[User] = None,
                   var groups: List[Group] = List.empty,
                   var essays: List[Essay] = List.empty)

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
    case Teacher(id, firstName, lastName, patronymic, email, birthday, _, _, _) =>
      Option((id, firstName, lastName, patronymic, email, birthday))
  }

  override def * =
    (id.?, firstName, lastName, patronymic, email, birthday) <>
      (toTeacher, fromTeacher)
}

object Teacher extends Repositorie with TeacherExtensions {
  val models = TableQuery(new Teachers(_))

  def getByUserId(userId: Int) = db.run(models.filter(_.id === userId).result.headOption)
  def getAll = db.run(models.joinEssayAndGroup.result).map(_.toTeacher)
  def getAllPublicTeachers = db.run(models.result)
}


trait TeacherExtensions {

  implicit class ToTeacher[C[_]](list: Seq[((((Teacher, UserEssay), Essay), TeacherGroup), Group)]) {
    def toTeacher = {
      var map: Map[Int, Teacher] = Map.empty
      list foreach {
        case ((((t, _), e), _), g) =>
          val id = t.id.get
          val teacher = if (map.isDefinedAt(id)) map(id) else { map += id -> t; t}
          if(!teacher.essays.exists(_.id == e.id)) teacher.essays = e :: teacher.essays
          if(!teacher.groups.exists(_.id == g.id)) teacher.groups = g :: teacher.groups
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }

  implicit class JoinEssayAndGroup[C[_]](query: Query[Teachers, Teacher, C]) {
    def joinEssayAndGroup = query
        .join(UserEssay.models).on(_.id === _.userId)
          .join(Essay.models).on(_._2.essayId === _.id)
        .join(TeacherGroup.models).on(_._1._1.id === _.teacherId)
          .join(Group.models).on(_._1._1._1.id === _.id)
  }

}