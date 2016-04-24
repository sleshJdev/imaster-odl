import javax.servlet.ServletContext

import by.slesh.bntu.imaster.persistence.DatabaseConnector
import by.slesh.bntu.imaster.web.controller.{StudentController, AppController}
import org.scalatra._
import org.slf4j.LoggerFactory

class Bootstraper extends LifeCycle {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    LOGGER.info("initialize context ... ")
    DatabaseConnector.initialize()
    context.mount(new AppController, "/api/*")
    context.mount(new StudentController, "/api/students/*")
    LOGGER.info("initialize context ... done!")
  }

  override def destroy(context: ServletContext) {
    LOGGER.info("destroy context ... ")
    DatabaseConnector.release()
    super.destroy(context)
    LOGGER.info("destroy context ... done")
  }
}