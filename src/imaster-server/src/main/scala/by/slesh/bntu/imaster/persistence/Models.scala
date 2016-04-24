package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
  * @author yauheni.putsykovich
  */
object Models {
  case class User(id: Option[Int],
                  username: String,
                  password: String,
                  firstName: String,
                  lastName: String,
                  patronymic: Option[String])

  case class Role(id: Option[Int],
                  name: String,
                  description: Option[String])

  case class UserExtended(user: User,
                          roles: List[Role])

  class UserRow(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username", O.Length(30, varying = true))
    def password = column[String]("password", O.Length(30, varying = true))
    def firstName = column[String]("firstName", O.Length(60, varying = true))
    def lastName = column[String]("lastName", O.Length(60, varying = true))
    def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))
    override def * : ProvenShape[User] = (id.?, username, password, firstName, lastName, patronymic) <> (User.tupled, User.unapply)
    def usernameIndex = index("idx_username", username, unique = true)
    def roles = userRoleTable.filter(_.userId === id).flatMap(_.role)
  }

  class RoleRow(tag: Tag) extends Table[Role](tag, "role") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.Length(15, varying = true))
    def description = column[Option[String]]("description", O.Length(100, varying = true))
    override def * : ProvenShape[Role] = (id.?, name, description) <> (Role.tupled, Role.unapply)
  }

  class UserRoleRow(tag: Tag) extends Table[(Int, Int)](tag, "user_role"){
    def userId = column[Int]("userId")
    def roleId = column[Int]("roleId")
    override def * : ProvenShape[(Int, Int)] = (userId, roleId)
    def user = foreignKey("fk_user", userId, userTable)(_.id)
    def role = foreignKey("fk_role", roleId, roleTable)(_.id)
  }

  class UserTable extends TableQuery(new UserRow(_))
  class RoleTable extends TableQuery(new RoleRow(_))
  class UserRoleTable extends TableQuery(new UserRoleRow(_))

  val userTable = new UserTable
  val roleTable = new RoleTable
  val userRoleTable = new UserRoleTable
}
