package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._

/**
  * @author slesh
  */
class UserRoles(tag: Tag) extends Table[(Int, Int)](tag, "user_role") {
  def userId = column[Int]("userId")

  def roleId = column[Int]("roleId")

  def user = foreignKey("fk__user_role__user_id__user_id", userId, User.models)(_.id)
  def role = foreignKey("fk__user_role__role_id__role_id", roleId, Role.models)(_.id)

  def id = primaryKey("pk_user_x_role", (userId, roleId))

  override def * = (userId, roleId)
}


object UserRole extends Repositorie {
  val models = TableQuery(new UserRoles(_))
}