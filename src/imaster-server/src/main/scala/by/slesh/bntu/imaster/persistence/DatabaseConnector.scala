package by.slesh.bntu.imaster.persistence

import java.sql.Date

import by.slesh.bntu.imaster.persistence.Models._
import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

/**
  * @author yauheni.putsykovich
  */

object DatabaseConnector {

  private val logger = LoggerFactory.getLogger(getClass)

  var instance: Option[Database] = None

  def db: Database = instance match {
    case Some(x) => x
    case _ =>
      logger.error("database not created")
      throw new IllegalAccessException("database not created")
  }

  def initialize(): Unit = {
    logger.info("initialization database ... ")
    val config = ConfigFactory.load()
    instance = Some(Database.forConfig(config.getString("databaseConfig")))
    logger.info("initialization database ... done")
  }

  def release(): Unit = {
    logger.info("release database resources ...")
    instance match {
      case x: Some[Database] =>
        logger.info("waiting for database shutdown ...")
        Await.ready(x.get.shutdown, 60 second)
        instance = None
        logger.info("database shutdown ...  done")
      case _ => ()
    }
    logger.info("release database resources ... done")
  }

  def fillData(): Unit = {
    val query = DBIO.seq(
      userTable ++= Seq(
        User(Some(1), "student", "studentp", "studentfn", "studentln", Some("studentp")),
        User(Some(2), "teacher", "teacherp", "teacherfn", "teacherln", Some("teacherp")),
        User(Some(3), "admin", "adminp", "adminfn", "adminln", Some("adminp"))
      ),
      roleTable ++= Seq(
        Role(Some(1), "user", Some("user role")),
        Role(Some(2), "student", Some("student role")),
        Role(Some(3), "teacher", Some("teacher role")),
        Role(Some(4), "admin", Some("admin role"))
      ),
      userRoleTable ++= Seq(
        (1, 1),
        (1, 2),
        (2, 1),
        (2, 3),
        (3, 1),
        (3, 4)
      ),
      studentTable ++= Seq(
        Student(Some(1), "vasya", "vasya", Some("vasya"), new Date(DateTime.now().getMillis)),
        Student(Some(2), "kolya", "kolya", Some("kolya"), new Date(DateTime.now().getMillis)),
        Student(Some(3), "petya", "petya", Some("petya"), new Date(DateTime.now().getMillis)),
        Student(Some(4), "vitalya", "vitalya", Some("vitalya"), new Date(DateTime.now().getMillis)),
        Student(Some(5), "igor", "igor", Some("igor"), new Date(DateTime.now().getMillis))
      )
    )
    Await.result(db.run(query), 60 second)
  }

  def createSchema(): Unit = {
    val schema = (userTable.schema ++ roleTable.schema ++ userRoleTable.schema ++ studentTable.schema).create
    Await.result(db.run(schema), 60 second)
  }

  def dropSchema(): Unit = {
    val drop = (userTable.schema ++ roleTable.schema ++ userRoleTable.schema ++ studentTable.schema).drop
    Await.result(db.run(drop), 60 second)
  }
}
