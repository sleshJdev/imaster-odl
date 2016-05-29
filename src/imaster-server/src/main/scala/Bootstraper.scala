import javax.servlet.ServletContext

import by.slesh.bntu.imaster.persistence.DatabaseSource
import by.slesh.bntu.imaster.web.controller._
import org.scalatra._
import org.slf4j.LoggerFactory

class Bootstraper extends LifeCycle {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    LOGGER.info("initialize context ... ")
    DatabaseSource.connect()
    context.mount(new AppController, "/api/*")
    context.mount(new StudentController, "/api/students/*")
    context.mount(new EssayController, "/api/essays/*")
    context.mount(new TeacherController, "/api/teachers/*")
    context.mount(new DocumentController, "/api/documents/*")
    context.mount(new StatusController, "/api/statuses/*")
    LOGGER.info("initialize context ... done!")
  }

  override def destroy(context: ServletContext) {
    LOGGER.info("destroy context ... ")
    DatabaseSource.release()
    super.destroy(context)
    LOGGER.info("destroy context ... done")
  }
}
