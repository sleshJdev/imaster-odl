package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector.db
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
 * @author yauheni.putsykovich
 */
case class User(id: Int,
                username: String,
                password: String,
                firstName: String,
                lastName: String,
                patronymic: Option[String])

class UserRow(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.Length(30, varying = true))
  def password = column[String]("password", O.Length(30, varying = true))
  def firstName = column[String]("firstName", O.Length(60, varying = true))
  def lastName = column[String]("lastName", O.Length(60, varying = true))
  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))
  override def * : ProvenShape[User] = (id, username, password, firstName, lastName, patronymic) <>(User.tupled, User.unapply)
}

class UserTable extends TableQuery(new UserRow(_))

class UserRepository extends UserTable {
  def getUserById(id: Int) = db.run(this.filter(_.id === id).result)
}
