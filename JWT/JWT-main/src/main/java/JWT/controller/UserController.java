package JWT.controller;

import JWT.jwt.JwtUtility;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Zimi Li
 */

@RestController
public class UserController {

    @Autowired
    JwtUtility jwtUtility;

    @GetMapping("login/{id}")
    public String login(@PathVariable Integer id) {
        // logic: verify

        return jwtUtility.genToken(id);
    }

    @PostMapping("check")
    public Integer check(@RequestBody String token) {
        DecodedJWT decoded = jwtUtility.verify(token);
        if (decoded == null) return -1;
        return decoded.getClaims().get("userId").asInt();
    }

    @GetMapping("get-id")
    public Integer getID(@RequestHeader Map<String, String> headers) {
        DecodedJWT decoded = jwtUtility.verify(headers.get("jwt"));
        if (decoded == null) return -1;
        return decoded.getClaims().get("userId").asInt();
    }
}
