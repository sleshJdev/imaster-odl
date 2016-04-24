package by.slesh.bntu.imaster.security

import org.scalatest._

/**
  * @author yauheni.putsykovich
  */
class TokenServiceTests extends FlatSpec with Matchers {
  val CLAIMS = Map("Hey" -> "foo")

  "A TokenService" should "create valid jwt-token" in {
    val token = TokenService.createToken(CLAIMS)
    assert(TokenService.validate(token))
  }

  it should "be equals a expected token" in {
    //    val expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJIZXkiOiJmb28ifQ==.e89b48f1e2016b78b805c1430d5a139e62bba9237ff3e34bad56dae6499b2647"
    val expectedToken = "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJIZXkiOiJmb28ifQ.mN_sJOimR3xPJEu5lAN9YHu_wlDuuIStCyJ4jtMVEICr9TLfOweNecAu76Pk1Xxr"
    val actualToken = TokenService.createToken(CLAIMS)
    assertResult(expectedToken)(actualToken)
  }

  it should "returns jwt-token which contains same data as source claims" in {
    val jwt = TokenService.createToken(CLAIMS)
    val claims: Option[Map[String, String]] = TokenService.parseToken(jwt)
    claims.isDefined should be(true)
    claims.get should equal(CLAIMS)
  }
}
