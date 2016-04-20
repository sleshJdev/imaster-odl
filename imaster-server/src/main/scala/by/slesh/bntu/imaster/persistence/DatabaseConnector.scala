package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

/**
  * @author yauheni.putsykovich
  */

object DatabaseConnector {
  private val logger = LoggerFactory.getLogger(getClass)

  var instance: Option[Database] = None
  val userTable = new UserTable
  val roleTable = new RoleTable

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
        User(1, "admin", "adminp", "adminfn", "adminln", Some("adminp")),
        User(1, "student", "studentp", "studentfn", "studentln", Some("studentp"))
      ),
      roleTable ++= Seq(
        Role(1, "student", Some("student role")),
        Role(2, "teacher", Some("teacher role")),
        Role(3, "admin", Some("admin role"))
      )
    )
    Await.result(db.run(query), 60 second)
  }

  def createSchema(): Unit = {
    val schema = (userTable.schema ++ roleTable.schema).create
    Await.result(db.run(schema), 60 second)
  }

  def dropSchema(): Unit = {
    val drop = (userTable.schema ++ roleTable.schema).drop
    Await.result(db.run(drop), 60 second)
  }
}
