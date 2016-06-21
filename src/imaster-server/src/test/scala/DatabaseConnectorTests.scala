import by.slesh.bntu.imaster.persistence.DatabaseSource._
import org.scalatest._

import scala.concurrent.Await.{result, ready}
import scala.concurrent.{Await, Awaitable}
import scala.concurrent.duration.DurationInt

/**
  * @author yauheni.putsykovich
  */
class DatabaseConnectorTests extends FlatSpec with BeforeAndAfter {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  def runAsyncAsPlain(await: Awaitable[Any]) = result(await, 60 second)

  "The Database connection" should "establish connection" in {
    connect()
    runAsyncAsPlain(dropSchema())
    runAsyncAsPlain(createSchema())
    runAsyncAsPlain(fillData())
    release()
  }

}
