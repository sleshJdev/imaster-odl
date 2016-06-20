package by.slesh.bntu.imaster.security

/**
  * @author slesh
  */
class UserNotFoundException(message: String, throwable: Throwable) extends Exception(message, throwable) {
  def this() = this(null, null)
  def this(message: String) = this(message, null)
  def this(throwable: Throwable) = this(null, throwable)
}
