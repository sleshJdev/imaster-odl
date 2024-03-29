package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.{StudentEssay, Student}
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory


class StudentController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)

  get("/")(Student.getAllPublicStudents)

  get("/:id") {
    val id = params("id").toInt
    logger.info("get student by id {}", id)
    Student.getById(id)
  }

  get("/:id/essay/exists") {
    val id = params("id").toInt
    logger.info("check essay is existing for student {}", id)
    StudentEssay.getByStudentId(id).map(_.isDefined)

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
