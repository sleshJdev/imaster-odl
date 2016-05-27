package by.slesh.bntu.imaster.persistence

import java.sql.{Date, SQLException}

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import slick.driver.H2Driver.api._

import scala.concurrent.Future

/**
  * @author yauheni.putsykovich
  */

object DatabaseConnector {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  private val logger = LoggerFactory.getLogger(getClass)

  var instance: Option[Database] = None

  def db: Database = instance match {
    case Some(x) => x
    case _ =>
      logger.error("database not created")
      throw new SQLException("database not created")
  }

  def connect(): Unit = {
    logger.info("connecting to database ... ")
    val config = ConfigFactory.load()
    instance = Some(Database.forConfig(config.getString("databaseConfig")))
    db
    logger.info("connection to database ... done")
  }

  def release(): Future[Unit] = {
    logger.info("release database resources ...")
    var shutdownFuture: Option[Future[Unit]] = None
    instance match {
      case Some(x) =>
        logger.info("waiting for database shutdown ...")
        shutdownFuture = Option(x.shutdown)
        instance = None
        logger.info("database shutdown ...  done")
      case _ => ()
    }
    logger.info("release database resources ... done")
    shutdownFuture match {
      case Some(x) => x
      case _ => throw new SQLException("release database error")
    }
  }

  val schemaDatabase = User.models.schema ++
    Role.models.schema ++
    UserRole.models.schema ++
    Student.models.schema ++
    Essay.models.schema ++
    StudentEssay.models.schema

  val fillQuery = DBIO.seq(
    User.models ++= Seq(
      User(Some(1), "student", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(2), "teacher", "teacherp", "teacherfn", "teacherln", Some("teacherp")),
      User(Some(3), "admin", "adminp", "adminfn", "adminln", Some("adminp"))
    ),
    Role.models ++= Seq(
      Role(Some(1), "user", Some("user role")),
      Role(Some(2), "student", Some("student role")),
      Role(Some(3), "teacher", Some("teacher role")),
      Role(Some(4), "admin", Some("admin role"))
    ),
    UserRole.models ++= Seq(
      (1, 1),
      (1, 2),
      (2, 1),
      (2, 3),
      (3, 1),
      (3, 4)
    )
    ,
    Student.models ++= Seq(
      Student(Some(1), "vasya", "vasya", Some("vasya"), new Date(DateTime.now().getMillis)),
      Student(Some(2), "kolya", "kolya", Some("kolya"), new Date(DateTime.now().getMillis)),
      Student(Some(3), "petya", "petya", Some("petya"), new Date(DateTime.now().getMillis)),
      Student(Some(4), "vitalya", "vitalya", Some("vitalya"), new Date(DateTime.now().getMillis)),
      Student(Some(5), "igor", "igor", Some("igor"), new Date(DateTime.now().getMillis))
    )
  )

  def fillData() = db.run(fillQuery)

  def createSchema() = db.run(schemaDatabase.create)

  def dropSchema() = db.run(schemaDatabase.drop)

}
