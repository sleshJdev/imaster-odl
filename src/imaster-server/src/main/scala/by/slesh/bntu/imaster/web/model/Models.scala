package by.slesh.bntu.imaster.web.model

import org.json4s.JObject

/**
  * @author yauheni.putsykovich
  */

  case class Account(username: String,
                     password: String,
                     loginAs: String)

  case class AuthData(token: String,
                      username: String,
                      roles: List[String],
                      personalInfoJson: Option[Any])