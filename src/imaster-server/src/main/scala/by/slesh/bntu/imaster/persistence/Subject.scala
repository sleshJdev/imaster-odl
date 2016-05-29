package by.slesh.bntu.imaster.persistence

import slick.lifted.TableQuery
import slick.driver.H2Driver.api._

/**
  * @author slesh
  */
case class Subject(var id: Option[Int],
                  var title: String,
                  var description: Option[String])

class Subjects(tag: Tag) extends Table[Subject](tag, "subject") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title", O.Length(50, varying = true))
  def description = column[Option[String]]("description", O.Length(50, varying = true))

  override def * = (id.?, title, description) <> ((Subject.apply _).tupled, Subject.unapply)
}

object Subject {
  val models = new TableQuery(new Subjects(_))


}
