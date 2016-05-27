package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector._
import by.slesh.bntu.imaster.persistence.Extensions._

import scala.concurrent.Future

/**
  * @author slesh
  */

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

case class User(id: Option[Int],
                username: String,
                password: String,
                firstName: String,
                lastName: String,
                patronymic: Option[String])

case class UserExtended(user: User,
                        roles: List[Role])

class Users(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def username = column[String]("username", O.Length(30, varying = true))

  def password = column[String]("password", O.Length(30, varying = true))

  def firstName = column[String]("firstName", O.Length(60, varying = true))

  def lastName = column[String]("lastName", O.Length(60, varying = true))

  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))

  def usernameIndex = index("idx_username", username, unique = true)

  override def * : ProvenShape[User] =
    (id.?, username, password, firstName, lastName, patronymic) <>
      ((User.apply _).tupled, User.unapply)
}

object User extends Repositorie {
  val models = TableQuery(new Users(_))

  def getById(id: Int): Future[Option[UserExtended]] = db.run(models.filter(_.id === id).joinRoles.result).map(_.toExtendedUser)

  def getAll: Future[Seq[UserExtended]] = db.run(models.joinRoles.result).map(_.toExtendedUsers)

  def getUserByName(username: String): Future[Option[UserExtended]] = {
    db.run(models.filter(_.username === username).joinRoles.result).map(_.toExtendedUser)
  }
}
