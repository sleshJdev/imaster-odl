package by.slesh.bntu.imaster.security

import authentikat.jwt._
import org.slf4j.LoggerFactory

/**
  * @author yauheni.putsykovich
  */
object TokenService {
  private val logger = LoggerFactory.getLogger(getClass)

  var secretKey = "secretkey"
  var algorithm = "HS384"

  def createToken(claims: Map[String, String]): String = {
    logger.info("create token: {}", claims)
    val header = JwtHeader(algorithm)
    val claimsSet = JwtClaimsSet(claims)
    JsonWebToken(header, claimsSet, secretKey)
  }

  def parseToken(tokenJson: String): Option[Map[String, String]] = {
    logger.info("parse token: {}", tokenJson)
    tokenJson match {
      case JsonWebToken(header, claimsSet, signature) =>
        claimsSet.asSimpleMap.toOption
      case x =>
        None
    }
  }

  def validate(tokenJson: String) = JsonWebToken.validate(tokenJson, secretKey)
}


