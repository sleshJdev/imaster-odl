package by.slesh.bntu.imaster.security

import authentikat.jwt._
import org.slf4j.LoggerFactory

/**
  * @author yauheni.putsykovich
  */
object TokenService {
  var secretKey = "secretkey"
  var algorithm = "HS384"

  def createToken(claims: Map[String, String]): String = {
    val header = JwtHeader(algorithm)
    val claimsSet = JwtClaimsSet(claims)
    JsonWebToken(header, claimsSet, secretKey)
  }

  def parseToken(tokenJson: String): Option[Map[String, String]] = {
    tokenJson match {
      case JsonWebToken(header, claimsSet, signature) =>
        claimsSet.asSimpleMap.toOption
      case x =>
        None
    }
  }

  def validate(tokenJson: String) = JsonWebToken.validate(tokenJson, secretKey)
}


