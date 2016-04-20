package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector.db
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
 * @author yauheni.putsykovich
 */
case class Role(id: Int,
                name: String,
                description: String)

class RoleRow(tag: Tag) extends Table[Role](tag, "role") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  override def * : ProvenShape[Role] = (id, name, description) <>(Role.tupled, Role.unapply)
}

class RoleTable extends TableQuery(new RoleRow(_))

class RoleRepository extends RoleTable {
  def getRoleById(id: Int): Future[Option[Role]] = db.run(filter(_.id === id).result.headOption)
}
