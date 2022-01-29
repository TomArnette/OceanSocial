package com.revature.ocean.utility;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtUtilityTest {

    JwtUtility jwtUtility;

    @BeforeEach
    public void Setup(){
        jwtUtility = new JwtUtility();
    }

    @Test
    public void verifySucceed(){
        String token = jwtUtility.genToken(2);

        assertNotNull(jwtUtility.verify(token));
    }

    @Test
    public void verifyNull(){
        assertNull(jwtUtility.verify(null));
    }

    @Test
    public void gentokenNotNull(){
        assertNotNull(jwtUtility.genToken(1));
    }

    //Not sure how to make this one throw an exception.

    /*
    @Test
    public void gentokenNull(){

        assertNull(jwtUtility.genToken(null));
    }
    */

    /*
    *     public String genToken(Integer userId) {
        System.out.println("jwtUtility.genToken");
        try {
            System.out.println("jwtUtility.genToken try block");
            return JWT.create().withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + timeLength))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            System.out.println("jwtUtility.genToken catch block");
            exception.printStackTrace();
        }
        return null;
    }

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
    * */

}
