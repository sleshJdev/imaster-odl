package by.slesh.bntu.imaster.security

import org.scalatest._

/**
 * @author yauheni.putsykovich
 */
class TokenServiceTests extends FlatSpec {
  val CLAIM = Map("Hey" -> "foo")

  "A TokenService" should "create valid jwt-token" in {
    val token = TokenService.createToken(CLAIM)
    assert(TokenService.validate(token))
  }

  ignore should "be equal a expected token" in {
    val expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJIZXkiOiJmb28ifQ==.e89b48f1e2016b78b805c1430d5a139e62bba9237ff3e34bad56dae6499b2647"
    val token = TokenService.createToken(CLAIM)
    assertResult(expectedToken)(token)
  }
}
