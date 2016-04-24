package by.slesh.bntu.imaster.persistence

import org.scalatest.{Matchers, BeforeAndAfterAll, FlatSpec}

/**
  * @author slesh
  */
class TestConfig extends FlatSpec with BeforeAndAfterAll with Matchers {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  override protected def beforeAll(): Unit = DatabaseConnector.initialize()
  override protected def afterAll(): Unit = DatabaseConnector.release()
}
