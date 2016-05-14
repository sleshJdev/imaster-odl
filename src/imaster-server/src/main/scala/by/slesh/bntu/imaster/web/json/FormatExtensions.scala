package by.slesh.bntu.imaster.web.json

import java.sql.Date

import org.joda.time.DateTime
import org.json4s._

/**
  * @author slesh
  */
object StringToInt extends CustomSerializer[Int](ser = format => ( {
  case JString(x) => x.toInt
}, {
  case x: Int => JInt(x)
}))

object DateToString extends CustomSerializer[Date](ser = format => ( {
  case JString(x) => new Date(DateTime.parse(x).getMillis)
}, {
  case x: Date => JString(x.toString)
}))
