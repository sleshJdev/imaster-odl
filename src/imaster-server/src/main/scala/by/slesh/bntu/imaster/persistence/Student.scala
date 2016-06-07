package by.slesh.bntu.imaster.persistence

import java.sql.Date

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */

case class Student(var id: Option[Int],
                   var firstName: String,
                   var lastName: String,
                   var patronymic: Option[String] = None,
                   var email: String,
                   var birthday: Date,
                   var groupId: Option[Int] = None,
                   var personalCardId: String,
                   var user: Option[User] = None,
                   var group: Option[Group] = None,
                   var essays: List[Essay] = List.empty)

class Students(tag: Tag) extends Table[Student](tag, "student") {
  def id = column[Int]("id", O.PrimaryKey)
  def firstName = column[String]("firstName", O.Length(60, varying = true))
  def lastName = column[String]("lastName", O.Length(60, varying = true))
  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))
  def email = column[String]("email", O.Length(60, varying = true))
  def birthday = column[Date]("birthday")
  def groupId = column[Int]("group_id")
  def personalCardId = column[String]("personal_card_id")
  def user = foreignKey("fk_student__user_id__user_id", id, User.models)(_.id, onDelete = ForeignKeyAction.Cascade)
  def group = foreignKey("fk_student__group_id__group_id", groupId, User.models)(_.id, onDelete = ForeignKeyAction.Cascade)

  type Data = (Option[Int], String, String, Option[String], String, Date, Option[Int], String)
  def toStudent: Data => Student = {
    case (id, firstName, lastName, patronymic, email, birthday, groupId, personalCardId) =>
      Student(id, firstName, lastName, patronymic, email, birthday, groupId, personalCardId)
  }
  def fromStudent: PartialFunction[Student, Option[Data]] = {
    case Student(id, firstName, lastName, patronymic, email, birthday, groupId, personalCardId, _, _, _) =>
      Option((id, firstName, lastName, patronymic, email, birthday, groupId, personalCardId))
  }
  override def * =
    (id.?, firstName, lastName, patronymic, email, birthday, groupId.?, personalCardId) <>
      (toStudent, fromStudent)
}

object Student extends Repositorie with StudentExtensions {
  val models = TableQuery(new Students(_))

  def getByUserId(userId: Int) = db.run(models.filter(_.id === userId).result.headOption)

  def getById(id: Int) = db.run(models.filter(_.id === id).joinEssay.result).map(_.toStudent.headOption)

  def getAllPublicStudents = db.run(models.result)

  def getAll = db.run(models.joinEssay.result).map(_.toStudent)

  def add(student: Student) = db.run((models returning models.map(_.id)) += student).map(_.toInt)
}


trait StudentExtensions {
  implicit class ToStudent[C[_]](list: Seq[((((Student, Option[UserEssay]), Option[Essay]), Option[Status]), Option[Group])]) {
    def toStudent = {
      var map: Map[Int, Student] = Map.empty
      list foreach {
        case ((((s, _), e), t), g) =>
          val id = s.id.get
          val student = if (map.isDefinedAt(id)) map(id) else { map += id -> s; s}
          if(e.isDefined && !student.essays.exists(_.id == e.orNull)) {
            e.get.status = t
            student.essays = e.get :: student.essays
          }
          student.group = g
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }

  implicit class JoinEssay[C[_]](query: Query[Students, Student, C]) {
    def joinEssay = query
        .joinLeft(UserEssay.models).on(_.id === _.userId)
        .joinLeft(Essay.models).on(_._2.map(_.essayId) === _.id)
        .joinLeft(Status.models).on(_._2.map(_.statusId) === _.id)
        .joinLeft(Group.models).on(_._1._1._1.groupId === _.id)
  }

}