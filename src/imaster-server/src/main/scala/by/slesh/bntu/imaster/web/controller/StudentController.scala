package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Models.Student
import by.slesh.bntu.imaster.persistence.Repositories._
import by.slesh.bntu.imaster.web.AbstractController
import org.slf4j.LoggerFactory


class StudentController extends AbstractController {
  override val logger = LoggerFactory.getLogger(getClass)
  val repository = new StudentRepository

  get("/?") {
    repository.getAll
  }

  post("/?") {
    val student = parsedBody.extract[Student]
    logger.info("add new student: {}", student)
    repository.add(student)
  }
}
