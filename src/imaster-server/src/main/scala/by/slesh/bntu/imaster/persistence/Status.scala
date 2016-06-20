package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

/**
  * @author slesh
  */

case class Status(var id: Option[Int],
                  var title: String,
                  var description: Option[String])

class Statuses(tag: Tag) extends Table[Status](tag, "status") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title", O.Length(50, varying = true))
  def description = column[Option[String]]("description", O.Length(50, varying = true))
  override def * = (id.?, title, description) <> ((Status.apply _).tupled, Status.unapply)
}

object Status {
  val models = new TableQuery(new Statuses(_))

  def getAll = db.run(models.result)
}
