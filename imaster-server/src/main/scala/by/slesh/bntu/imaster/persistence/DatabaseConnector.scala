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
  private val LOGGER = LoggerFactory.getLogger(getClass)

  private var instance: Option[Database] = None

  def db: Database = instance match {
    case Some(x) => x
    case _ => throw new IllegalAccessException("database not created")
  }

  def initialize(): Unit = {
    LOGGER.info("initialization database ... ")
    val config = ConfigFactory.load()
    instance = Some(Database.forConfig(config.getString("databaseConfig")))
    LOGGER.info("initialization database ... done")
  }

  def release(): Unit = {
    LOGGER.info("release database resources ...")
    instance match {
      case x: Some[Database] =>
        LOGGER.info("waiting for database shutdown ...")
        Await.ready(x.get.shutdown, 60 second)
        LOGGER.info("database shutdown ...  done")
      case _ => ()
    }
    LOGGER.info("release database resources ... done")
  }

  def fillData(): Unit = {
    val query = DBIO.seq(
      Tables.user ++= Seq(
        User(1, "admin", "adminp", "adminfn", "adminln", Some("adminp")),
        User(1, "student", "studentp", "studentfn", "studentln", Some("studentp"))
      )
    )
    db.run(query)
  }

  def createSchema(): Unit = {
    db.run(Tables.user.schema.create)
  }

  def dropSchema(): Unit = {
    db.run(Tables.user.schema.drop)
  }
}
