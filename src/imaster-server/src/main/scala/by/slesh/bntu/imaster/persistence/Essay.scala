package by.slesh.bntu.imaster.persistence

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */
case class Essay(var id: Option[Int],
                 var title: String,
                 var fileId: String,
                 var description: Option[String] = None,
                 var statusId: Int,
                 var status: Option[Status] = None)

class Essays(tag: Tag) extends Table[Essay](tag, "essay") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title", O.Length(140, varying = true))
  def fileId = column[String]("file_id", O.Length(60, varying = true))
  def description = column[Option[String]]("description")
  def statusId = column[Int]("status_id")
  def status = foreignKey("fk_essay__status_id__status_id", statusId, Status.models)(_.id)

  type Data = (Option[Int], String, String, Option[String], Int)

  def toEssay: Data => Essay = {
    case (id, title, fileId, description, statusId) =>
      Essay(id, title, fileId, description, statusId)
  }

  def fromEssay: PartialFunction[Essay, Option[Data]] = {
    case Essay(id, title, fileId, description, statusId, _) =>
      Option((id, title, fileId, description, statusId))
  }

  override def * = (id.?, title, fileId, description, statusId) <> (toEssay, fromEssay)
}

object Essay extends Repositorie with EssayExtensions {
  val models = TableQuery(new Essays(_))

  def getAll: Future[Seq[Essay]] = {
    db.run(models.joinStatus.result).map(_.toEssay)
  }
  def getById(id: Int): Future[Option[Essay]] = {
    db.run(models.filter(_.id === id).joinStatus.result).map(_.toEssay.headOption)
  }
  def add(essay: Essay): Future[Int] = {
    var query = models returning models.map(_.id) += essay
    db.run(query).map(_.toInt)
  }
}

trait EssayExtensions {

  implicit class ToEssay[C[_]](list: Seq[(Essay, Option[Status])]) {
    def toEssay = {
      var map: Map[Int, Essay] = Map.empty
      list foreach {
        case (e, s) =>
          val id = e.id.get
          val essay = if (map.isDefinedAt(id)) map(id) else { map += id -> e; e}
          essay.status = s
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }

  implicit class JoinStatus[C[_]](query: Query[Essays, Essay, C]) {
    def joinStatus = query
      .joinLeft(Status.models).on(_.statusId === _.id)
  }

}

