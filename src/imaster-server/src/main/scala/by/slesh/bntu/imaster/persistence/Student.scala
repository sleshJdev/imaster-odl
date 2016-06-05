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
                   var userId: Option[Int] = None,
                   var groupId: Option[Int] = None,
                   var essayId: Option[Int] = None,
                   var personalCardId: String,
                   var user: Option[User] = None,
                   var group: Option[Group] = None,
                   var essay: Option[Essay] = None)

class Students(tag: Tag) extends Table[Student](tag, "student") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("firstName", O.Length(60, varying = true))
  def lastName = column[String]("lastName", O.Length(60, varying = true))
  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))
  def email = column[String]("email", O.Length(60, varying = true))
  def birthday = column[Date]("birthday")
  def userId = column[Int]("user_id")
  def groupId = column[Int]("group_id")
  def essayId = column[Int]("essay_id")
  def personalCardId = column[String]("personal_card_id")
  def user = foreignKey("fk_student__user_id__user_id", userId, User.models)(_.id, onDelete = ForeignKeyAction.Cascade)
  def group = foreignKey("fk_student__group_id__group_id", groupId, User.models)(_.id, onDelete = ForeignKeyAction.Cascade)
  def essay = foreignKey("fk_student__essay_id__essay_id", essayId, Essay.models)(_.id)
  type Data = (Option[Int], String, String, Option[String], String, Date, Option[Int], Option[Int], Option[Int], String)
  def toStudent: Data => Student = {
    case (id, firstName, lastName, patronymic, email, birthday, userId, groupId, essayId, personalCardId) =>
      Student(id, firstName, lastName, patronymic, email, birthday, userId, groupId, essayId, personalCardId)
  }
  def fromStudent: PartialFunction[Student, Option[Data]] = {
    case Student(id, firstName, lastName, patronymic, email, birthday, userId, groupId, essayId, personalCardId, _, _, _) =>
      Option((id, firstName, lastName, patronymic, email, birthday, userId, groupId, essayId, personalCardId))
  }
  override def * =
    (id.?, firstName, lastName, patronymic, email, birthday, userId.?, groupId.?, essayId.?, personalCardId) <>
      (toStudent, fromStudent)
}

object Student extends Repositorie with StudentExtensions {
  val models = TableQuery(new Students(_))

  def getById(id: Int): Future[Option[Student]] = {
    db.run(models.filter(_.id === id).joinEssay.result).map(_.toStudent.headOption)
  }

  def getAll: Future[Seq[Student]] = {
    db.run(models.joinEssay.result).map(_.toStudent)
  }

  def add(student: Student) = {
    var query = (models returning models.map(_.id)) += student
    db.run(query).map(_.toInt)
  }
}


trait StudentExtensions {

  implicit class ToStudent[C[_]](list: Seq[((Student, Option[Essay]), Option[Group])]) {
    def toStudent = {
      var map: Map[Int, Student] = Map.empty
      list foreach {
        case ((s, e), g) =>
          val id = s.id.get
          val student = if (map.isDefinedAt(id)) map(id) else { map += id -> s; s}
          student.essay = e
          student.group = g
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }

  implicit class JoinEssay[C[_]](query: Query[Students, Student, C]) {
    def joinEssay = query
      .joinLeft(Essay.models).on(_.essayId === _.id)
      .joinLeft(Group.models).on(_._1.groupId === _.id)
  }

}