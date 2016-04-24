package by.slesh.bntu.imaster.web.controller

import by.slesh.bntu.imaster.persistence.Repositories._
import by.slesh.bntu.imaster.web.AbstractController

/**
  * Created by slesh on 4/23/16.
  */
class StudentController extends AbstractController {
  val userRepository = new UserRepository

  get("/?"){
    authenticate()
    //todo: replace on students
    userRepository.getAllUsers
  }
}
