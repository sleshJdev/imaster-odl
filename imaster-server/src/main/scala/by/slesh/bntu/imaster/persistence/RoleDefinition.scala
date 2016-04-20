package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector.db
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape
import scala.concurrent.Future

/**
 * @author yauheni.putsykovich
 */
case class Role(id: Int,
                name: String,
                description: Option[String])

class RoleRow(tag: Tag) extends Table[Role](tag, "role") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Length(15, varying = true))
  def description = column[Option[String]]("description", O.Length(100, varying = true))
  override def * : ProvenShape[Role] = (id, name, description) <> (Role.tupled, Role.unapply)
}

class RoleTable extends TableQuery(new RoleRow(_))

class RoleRepository extends RoleTable {
  def getRoleByName(name: String): Future[Option[Role]] = db.run(filter(_.name === name).result.headOption)
  def getRoleById(id: Int): Future[Option[Role]] = db.run(filter(_.id === id).result.headOption)
}
