package by.slesh.bntu.imaster.web.json

import org.json4s._
import org.json4s.JsonAST.JString

/**
  * @author slesh
  */
object StringToInt extends CustomSerializer[Int](ser = format => ( {
  case JString(x) => x.toInt
}, {
  case x: Int => JInt(x)
}))
