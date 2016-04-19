package by.slesh.bntu.imaster.security

import authentikat.jwt._
import org.slf4j.LoggerFactory

/**
 * @author yauheni.putsykovich
 */
object TokenService {
  private val LOGGER = LoggerFactory.getLogger(getClass)

  def algorithm = "HS256"
  def secretKey = "secretkey"

  def createToken(claim: Map[String, String]): String = {
    val header = JwtHeader(algorithm)
    val claimsSet = JwtClaimsSet(claim)
    val jwt: String = JsonWebToken(header, claimsSet, secretKey)
    LOGGER.info("has been created jwt-token: {} with alg: {}, claims: {} and key: {}", jwt, algorithm, claim, secretKey)
    jwt
  }

  def validate(jwt: String) = JsonWebToken.validate(jwt, secretKey)
}
