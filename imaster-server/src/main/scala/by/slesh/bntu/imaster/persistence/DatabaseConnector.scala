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

  private var instance: Option[Database] = None
  private val userTable = new UserTable

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
      )
    )
    db.run(query)
  }

  def createSchema(): Unit = {
    db.run(userTable.schema.create)
  }

  def dropSchema(): Unit = {
    db.run(userTable.schema.drop)
  }
}
