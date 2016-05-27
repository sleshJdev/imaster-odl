package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._

/**
  * @author slesh
  */
class UserRoles(tag: Tag) extends Table[(Int, Int)](tag, "user_role") {
  def userId = column[Int]("userId")

  def roleId = column[Int]("roleId")

  def id = primaryKey("pk_user_x_role", (userId, roleId))

  override def * = (userId, roleId)
}


object UserRole extends Repositorie {
  val models = TableQuery(new UserRoles(_))
}