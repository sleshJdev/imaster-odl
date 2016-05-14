package by.slesh.bntu.imaster.web.model

/**
  * @author yauheni.putsykovich
  */
case class Account(username: String,
                   password: String)

case class AuthData(token: String,
                    username: String,
                    roles: List[String])