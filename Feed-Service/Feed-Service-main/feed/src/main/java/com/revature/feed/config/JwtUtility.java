package com.revature.feed.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 */
@Component
public class JwtUtility {
    public static final String SECRET = "Ocean-Kevin-Child";
    public static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    public static final JWTVerifier verifier = JWT.require(algorithm).build();


    /**
    * @param token
    *
    * @return validates the token is not null and valid
    */
    public DecodedJWT verify(String token) {
        if(token == null){
            return null;
        }
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
        }
        return null;
    }

}