package by.slesh.bntu.imaster.persistence

import java.sql.Date

import by.slesh.bntu.imaster.persistence.DatabaseSource._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author slesh
  */
case class Document(var id: Option[Int],
                    var title: Option[String],
                    var fileId: Option[String],
                    var description: Option[String],
                    var loadedDate: Option[Date],
                    var loadedBy: Option[Int],
                    var subjectId: Option[Int],
                    var teacher: Option[User] = None,
                    var subject: Option[Subject] = None)

class Documents(tag: Tag) extends Table[Document](tag, "document"){
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title", O.Length(140, varying = true))
  def fileId = column[String]("file_id", O.Length(60, varying = true))
  def description = column[Option[String]]("description")
  def loadedDate = column[Date]("loaded_date")
  def loadedById = column[Int]("user_id")
  def subjectId = column[Int]("subject_id")

  def teacher = foreignKey("fk_document__loaded_by_id__user_id", loadedById, User.models)(_.id)
  def subject = foreignKey("fk_document__subject_id__subject_id", loadedById, User.models)(_.id)

  type Data = (Option[Int], String, String, Option[String], Date, Int, Int)
  def toDocument: Data => Document = {
    case (id, title, fileId, description, loadedDate, loadedById, subjectId) =>
      Document(id, Option(title), Option(fileId), description, Option(loadedDate), Option(loadedById), Option(subjectId))
  }
  def fromDocument: PartialFunction[Document, Option[Data]] = {
    case Document(id, title, fileId, description, loadedDate, loadedById, subjectId, teacher, subject) =>
      Option((id, title.get, fileId.get, description, loadedDate.get, loadedById.getOrElse(teacher.get.id.get), subjectId.getOrElse(subject.get.id.get)))
  }
  override def * = (id.?, title, fileId, description, loadedDate, loadedById, subjectId) <> (toDocument, fromDocument)
}

object Document extends Repositorie with DocumentExtensions {
  val models = TableQuery(new Documents(_))

  def getAll: Future[Seq[Document]] = {
    db.run(models.joinSubject.result).map(_.toDocument)
  }
  def getById(id: Int): Future[Option[Document]] = {
    db.run(models.filter(_.id === id).joinSubject.result).map(_.toDocument.headOption)
  }
  def add(essay: Document): Future[Int] = {
    db.run(models insertOrUpdate essay).map(_.toInt)
  }
}

trait DocumentExtensions {

  implicit class ToDocument[C[_]](list: Seq[((Document, Option[Subject]), Option[User])]) {
    def toDocument = {
      var map: Map[Int, Document] = Map.empty
      list foreach {
        case ((d, s), u) =>
          val id = d.id.get
          val document = if (map.isDefinedAt(id)) map(id) else { map += id -> d; d}
          document.subject = s
          document.teacher = u
        case _ => throw new IllegalArgumentException("bad data format")
      }
      map.values.toList
    }
  }

  implicit class JoinSubject[C[_]](query: Query[Documents, Document, C]) {
    def joinSubject = query
      .joinLeft(Subject.models).on(_.subjectId === _.id)
      .joinLeft(User.models).on(_._1.loadedById === _.id)
  }

}
