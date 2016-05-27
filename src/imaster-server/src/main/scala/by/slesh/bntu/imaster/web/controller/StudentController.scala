package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Student
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory


class StudentController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/?") {
    logger.info("get all students")
    Student.getAll
  }

  get("/:id") {
    val id = params("id").toInt
    logger.info("get student by id {}", id)
    Student.getById(id)
  }

  post("/?") {
    val student = parsedBody.extract[Student]
    logger.info("add new student: {}", student)
    Student.add(student) map { id =>
      logger.info("student id: {}", id)
    }
  }

  put("/?") {
    val student = parsedBody.extract[Student]
    logger.info("update student: {}", student)
    Student.add(student)
  }
}
