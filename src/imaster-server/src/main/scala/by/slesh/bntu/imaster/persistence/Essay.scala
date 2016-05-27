package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseConnector._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */
case class Essay(var id: Option[Int],
                 var title: String,
                 var fileId: String,
                 var description: Option[String])


class Essays(tag: Tag) extends Table[Essay](tag, "essay") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title", O.Length(140, varying = true))

  def status = column[String]("status", O.Length(30, varying = true))

  def fileName = column[String]("file_name", O.Length(60, varying = true))

  def description = column[Option[String]]("description")

  override def * =
    (id.?, title, fileName, description) <>
      ((Essay.apply _).tupled, Essay.unapply)
}

object Essay extends Repositorie {
  val models = TableQuery(new Essays(_))

  def getAll: Future[Seq[Essay]] = db.run(models.result)

  def getById(id: Int): Future[Option[Essay]] = db.run(models.filter(_.id === id).result.headOption)

  def add(essay: Essay): Future[Int] = db.run(models insertOrUpdate essay).map(_.toInt)
}