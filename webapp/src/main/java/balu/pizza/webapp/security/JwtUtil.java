package balu.pizza.webapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * A class for generating and validating JWT-token
 * <p>
 *     When accessing through the project's REST API, authentication is required.
 *     If authorization is successful, a generated, unique JWT token is returned to the user in the response.
 *     By adding this token to the header of all subsequent requests, the user can confirm that the requests come from an authorized user.
 * </p>
 * <p>
 *     The token has a validity of 60 minutes.
 *     If the user wants to interact with the server for longer, he/she needs to reauthorize and get a new token
 * </p>
 * @author Sergii Bugaienko
 */

@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    /**
     * Generate JWT-token
     * @param username
     * @return JWT-token
     */
    public String generateToken(String username){
        Date experationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Pizza app")
                .withExpiresAt(experationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Validate JWT-token
     * @param token
     * @return
     * @throws JWTVerificationException - the token did not pass validation
     */
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Pizza app")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
