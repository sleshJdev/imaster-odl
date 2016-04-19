import by.slesh.bntu.imaster.persistence.DatabaseConnector
import org.scalatest._

/**
  * @author yauheni.putsykovich
  */
class DatabaseConnectorTests extends FlatSpec with BeforeAndAfter {
  "The Database connection" should "establish" in {
    DatabaseConnector.initialize()
  }

  it should "create database schema" in {
    DatabaseConnector.createSchema()
  }

  it should "inser data to table" in {
    DatabaseConnector.fillData()
  }

  it should "release all connection" in {
    DatabaseConnector.release()
  }

  override protected def after(fun: => Any): Unit = DatabaseConnector.release()
}
