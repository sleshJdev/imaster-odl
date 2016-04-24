package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector.db
import by.slesh.bntu.imaster.persistence.Models._
import com.sun.media.sound.InvalidDataException
import slick.driver.H2Driver.api._

import scala.collection.mutable
import scala.concurrent.Future
import scala.language.higherKinds

/**
  * @author yauheni.putsykovich
  */
object Repositories {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  implicit class UserExtensions[C[_]](query: Query[UserRow, User, C]){
    def joinRoles = query
      .join(userRoleTable).on(_.id === _.userId)
      .join(roleTable).on(_._2.roleId === _.id)
  }
  implicit class UserExtendedExtension(list: Seq[((User, (Int, Int)), Role)]) {
    def toExtendedUsers = {
      val map: mutable.Map[User, List[Role]] = mutable.Map.empty
      list.map({
        case ((u, _), r) =>
          if (!map.isDefinedAt(u)) map += (u -> List.empty)
          if (map.isDefinedAt(u)) map += (u -> (r :: map(u)))
        case _ => throw new IllegalArgumentException("bad data format")
      })
      map.map(UserExtended.tupled).toList
    }
    def toExtendedUser = toExtendedUsers.headOption
  }

  class UserRepository {
    def getAllUsers: Future[Seq[UserExtended]] =
      db.run(userTable.joinRoles.result).map(_.toExtendedUsers)

    def getUserById(id: Int): Future[Option[UserExtended]] =
      db.run(userTable.filter(_.id === id).joinRoles.result).map(_.toExtendedUser)

    def getUserByName(username: String): Future[Option[User]] =
      db.run(userTable.filter(_.username === username).result.headOption)
  }

  class RoleRepository {
    def getRoleByName(name: String): Future[Option[Role]] =
      db.run(roleTable.filter(_.name === name).result.headOption)

    def getRoleById(id: Int): Future[Option[Role]] =
      db.run(roleTable.filter(_.id === id).result.headOption)
  }

  class StudentRepository {
    def getAllStudents: Future[Seq[Student]] =
      db.run(studentTable.result)

    def getStudentById(id: Int): Future[Option[Student]] =
      db.run(studentTable.filter(_.id === id).result.headOption)
  }
}
