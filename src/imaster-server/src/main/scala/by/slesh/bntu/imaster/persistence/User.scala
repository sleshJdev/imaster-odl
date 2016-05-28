package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector._
import slick.driver.H2Driver.api._
import scala.concurrent.Future

/**
  * @author slesh
  */


case class User(id: Option[Int],
                username: String,
                password: String,
                firstName: String,
                lastName: String,
                patronymic: Option[String],
                var roles: Option[List[Role]] = Option(List.empty))

class Users(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def username = column[String]("username", O.Length(30, varying = true))

  def password = column[String]("password", O.Length(30, varying = true))

  def firstName = column[String]("firstName", O.Length(60, varying = true))

  def lastName = column[String]("lastName", O.Length(60, varying = true))

  def patronymic = column[Option[String]]("patronymic", O.Length(60, varying = true))

  def usernameIndex = index("idx_username", username, unique = true)

  type Data = (Option[Int], String, String, String, String, Option[String])

  def toUser: Data => User = {
    case (id, username, password, firstName, lastName, patronymic) =>
      User(id, username, password, firstName, lastName, patronymic)
  }

  def fromUser: PartialFunction[User, Option[Data]] = {
    case User(id, username, password, firstName, lastName, patronymic, _) =>
      Option((id, username, password, firstName, lastName, patronymic))
  }

  override def * = (id.?, username, password, firstName, lastName, patronymic) <>(toUser, fromUser)
}

object User extends Repositorie with UserExtensions {
  val models = TableQuery(new Users(_))

  def getById(id: Int): Future[Option[User]] = db.run(models.filter(_.id === id).joinRoles.result).map(_.toUsers.headOption)

  def getAll: Future[Seq[User]] = db.run(models.joinRoles.result).map(_.toUsers)

  def getUserByName(username: String): Future[Option[User]] = db.run(models.filter(_.username === username).joinRoles.result).map(_.toUsers.headOption)
}

trait UserExtensions {

  implicit class UserExtension[C[_]](query: Query[Users, User, C]) {
    def joinRoles = query
      .join(UserRole.models).on(_.id === _.userId)
      .join(Role.models).on(_._2.roleId === _.id)
  }

  implicit class UserExtendedExtension(list: Seq[((User, (Int, Int)), Role)]) {
    def toUsers = {
      var map: Map[Int, User] = Map.empty
      list foreach {
        case ((u, _), r) =>
          val id = u.id.get
          val user = if(map.isDefinedAt(id)) map(id) else { map += id -> u; u }
          user.roles = Some(r :: user.roles.get)
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }
}


