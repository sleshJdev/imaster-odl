package by.slesh.bntu.imaster.security

import authentikat.jwt._
import org.slf4j.LoggerFactory

/**
  * @author yauheni.putsykovich
  */
object TokenService {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  var secretKey = "secretkey"
  var algorithm = "HS384"

  def createToken(claim: Map[String, String]): String = {
    val header = JwtHeader(algorithm)
    val claimsSet = JwtClaimsSet(claim)
    JsonWebToken(header, claimsSet, secretKey)
  }

  def parseToken(jwt: String): Option[Map[String, String]] = {
    jwt match {
      case JsonWebToken(header, claimsSet, signature) =>
        claimsSet.asSimpleMap.toOption
      case x =>
        None
    }
  }

  def validate(jwt: String) = JsonWebToken.validate(jwt, secretKey)
}
