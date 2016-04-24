package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Repositories._
import by.slesh.bntu.imaster.web.AbstractController


class StudentController extends AbstractController {
  val repository = new StudentRepository

  get("/?") {
    authenticate()
    repository.getAllStudents
  }
}
