package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector.db
import by.slesh.bntu.imaster.persistence.Extensions._
import by.slesh.bntu.imaster.persistence.Models._
import org.slf4j.LoggerFactory
import slick.driver.H2Driver.api._

import scala.concurrent.Future
import scala.language.higherKinds

/**
  * @author yauheni.putsykovich
  */
object Repositories {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  trait Repository[E, ID]{
    def getAll: Future[Seq[E]]

    def getById(id: ID): Future[Option[E]]

    def add(item: E): Future[ID]
  }

  class UserRepository extends Repository[UserExtended, Int] {
    val logger = LoggerFactory.getLogger(getClass)

    override def getById(id: Int): Future[Option[UserExtended]] = db.run(userTable.filter(_.id === id).joinRoles.result).map(_.toExtendedUser)

    override def getAll: Future[Seq[UserExtended]] = db.run(userTable.joinRoles.result).map(_.toExtendedUsers)

    //todo: add implementaion
    override def add(item: UserExtended): Future[Int] = ???

    def getUserByName(username: String): Future[Option[UserExtended]] = {
      logger.info("find user by name: {}", username)
      db.run(userTable.filter(_.username === username).joinRoles.result).map(_.toExtendedUser)
    }
  }

  class RoleRepository extends Repository[Role, Int] {
    override def getById(id: Int): Future[Option[Role]] = db.run(roleTable.filter(_.id === id).result.headOption)

    override def getAll: Future[Seq[Role]] = db.run(roleTable.result)

    //todo: add implementaion
    override def add(item: Role): Future[Int] = ???

    def getRoleByName(name: String): Future[Option[Role]] = db.run(roleTable.filter(_.name === name).result.headOption)
  }

  class StudentRepository extends Repository[Student, Int] {
    override def getById(id: Int): Future[Option[Student]] = db.run(studentTable.filter(_.id === id).result.headOption)

    override def getAll: Future[Seq[Student]] = db.run(studentTable.result)

    def add(student: Student): Future[Int] = db.run(studentTable insertOrUpdate student).map(_.toInt)
  }
}
