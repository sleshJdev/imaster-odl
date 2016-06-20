import by.slesh.bntu.imaster.persistence.DatabaseSource._
import org.scalatest._

import scala.concurrent.Await.ready
import scala.concurrent.Awaitable
import scala.concurrent.duration.DurationInt

/**
  * @author yauheni.putsykovich
  */
class DatabaseConnectorTests extends FlatSpec with BeforeAndAfter {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  def runAsyncAsPlain(await: Awaitable[Any]) = ready(await, 60 second)

  "The Database connection" should "establish connection" in {
    connect()
  }

  it should "dropped database schema" in {
    runAsyncAsPlain(dropSchema())
  }

  it should "create database schema" in {
    runAsyncAsPlain(createSchema())
  }

  it should "inser data to table" in {
    runAsyncAsPlain(fillData())
  }

  it should "release all connection" in {
    release()
  }

  override protected def after(fun: => Any): Unit = release()
}
