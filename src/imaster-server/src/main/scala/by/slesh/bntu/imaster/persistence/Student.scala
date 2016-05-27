package by.slesh.bntu.imaster.persistence

import java.sql.Date

import by.slesh.bntu.imaster.persistence.DatabaseConnector._
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

/**
  * @author slesh
  */

case class Student(id: Option[Int],
                   firstName: String,
                   lastName: String,
                   patronymic: Option[String],
                   birthday: Date)

class Students(tag: Tag) extends Table[Student](tag, "student") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def firstName = column[String]("firstName", O.Length(60, varying = true))

  def lastName = column[String]("lastName", O.Length(60, varying = true))

  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))

  def birthday = column[Date]("birthday")

  override def * : ProvenShape[Student] =
    (id.?, firstName, lastName, patronymic, birthday) <>((Student.apply _).tupled, Student.unapply)
}

object Student extends Repositorie {
  val models = TableQuery(new Students(_))

  def getById(id: Int): Future[Option[Student]] = {
    db.run(models.filter(_.id === id).result.headOption)
  }

  def getAll: Future[Seq[Student]] = db.run(models.result)

  def add(student: Student): Future[Int] = db.run(models insertOrUpdate student).map(_.toInt)
}
