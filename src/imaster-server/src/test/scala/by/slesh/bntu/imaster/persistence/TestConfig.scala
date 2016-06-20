package by.slesh.bntu.imaster.persistence

import org.scalatest.{Inspectors, Matchers, BeforeAndAfterAll, FlatSpec}

/**
  * @author slesh
  */
class TestConfig extends FlatSpec with BeforeAndAfterAll with Matchers with Inspectors {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  override protected def beforeAll(): Unit = DatabaseSource.connect()
  override protected def afterAll(): Unit = DatabaseSource.release()
}
