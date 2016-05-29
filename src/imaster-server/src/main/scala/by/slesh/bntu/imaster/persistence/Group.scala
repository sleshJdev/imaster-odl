package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._

/**
  * @author slesh
  */
case class Group(var id: Option[Int],
                  var title: String,
                  var description: Option[String])

class Groups(tag: Tag) extends Table[Group](tag, "group") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title", O.Length(50, varying = true))

  def description = column[Option[String]]("description", O.Length(50, varying = true))

  override def * = (id.?, title, description) <> ((Group.apply _).tupled, Group.unapply)
}

object Group {
  val models = new TableQuery(new Groups(_))


}
