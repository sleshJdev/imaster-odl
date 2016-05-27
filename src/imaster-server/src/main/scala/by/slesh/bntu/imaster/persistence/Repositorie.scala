package by.slesh.bntu.imaster.persistence

import scala.language.higherKinds

/**
  * @author yauheni.putsykovich
  */
trait Repositorie {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
}
