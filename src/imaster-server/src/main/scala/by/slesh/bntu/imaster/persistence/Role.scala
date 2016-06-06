package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */
case class Role(id: Option[Int],
                name: String,
                description: Option[String])

class Roles(tag: Tag) extends Table[Role](tag, "role") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(15, varying = true))

  def description = column[String]("description", O.Length(100, varying = true))

  override def * =
    (id.?, name, description.?) <>
      ((Role.apply _).tupled, Role.unapply)
}


object Role extends Repositorie {
  val models = TableQuery(new Roles(_))

  val USER = "user"
  val STUDENT = "student"
  val TEACHER = "teacher"
  val ADMIN = "admin"

  def getById(id: Int): Future[Option[Role]] = {
    db.run(models.filter(_.id === id).result.headOption)
  }

  def getAll: Future[Seq[Role]] = {
    db.run(models.result)
  }

  def getRoleByName(name: String): Future[Option[Role]] = {
    db.run(models.filter(_.name === name).result.headOption)
  }
}
