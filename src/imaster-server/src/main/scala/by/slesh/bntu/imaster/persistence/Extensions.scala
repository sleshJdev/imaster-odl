package by.slesh.bntu.imaster.persistence

import slick.driver.H2Driver.api._

import scala.collection.mutable

/**
  * @author slesh
  */
object Extensions {

  implicit class UserExtensions[C[_]](query: Query[Users, User, C]) {
    def joinRoles = query
      .join(UserRole.models).on(_.id === _.userId)
      .join(Role.models).on(_._2.roleId === _.id)
  }

  implicit class UserExtendedExtension(list: Seq[((User, (Int, Int)), Role)]) {
    def toExtendedUsers = {
      val map: mutable.Map[User, List[Role]] = mutable.Map.empty
      list.map({
        case ((u, _), r) =>
          if (!map.isDefinedAt(u)) map += (u -> List.empty)
          if (map.isDefinedAt(u)) map += (u -> (r :: map(u)))
        case _ => throw new IllegalArgumentException("bad data format")
      })
      map.map(UserExtended.tupled).toList
    }

    def toExtendedUser = toExtendedUsers.headOption
  }

}
