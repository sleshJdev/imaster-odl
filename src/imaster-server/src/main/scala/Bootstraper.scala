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
    context.mount(new AppController, "/*")
    context.mount(new StudentController, "/students/*")
    context.mount(new EssayController, "/essays/*")
    context.mount(new TeacherController, "/teachers/*")
    context.mount(new DocumentController, "/documents/*")
    context.mount(new StatusController, "/statuses/*")
    context.mount(new RolesController, "/roles/*")
    LOGGER.info("initialize context ... done!")
  }

  override def destroy(context: ServletContext) {
    LOGGER.info("destroy context ... ")
    DatabaseSource.release()
    super.destroy(context)
    LOGGER.info("destroy context ... done")
  }
}
