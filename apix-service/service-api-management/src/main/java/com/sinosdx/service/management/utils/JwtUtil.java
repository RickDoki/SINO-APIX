package com.sinosdx.service.management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author wendy
 * @date 2021/6/15
 */
@Slf4j
public class JwtUtil {

    public static String createJwt(String secretKey, Map<String, String> claimMap, long timeoutMs) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm al = Algorithm.HMAC256(secretKey);
        JWTCreator.Builder jwtBuilder = JWT.create().withIssuer("sinosdx").withSubject("sinosdx").withExpiresAt(new Date(System.currentTimeMillis() + timeoutMs));
        if (MapUtils.isNotEmpty(claimMap)) {
            claimMap.forEach(jwtBuilder::withClaim);
        }

        return jwtBuilder.sign(al);
    }

    public static Map<String, Claim> verifyJwt(String secretKey, String token) {
        if (StringUtils.isEmpty(secretKey)) {
            secretKey = "this_is_so_secret";
        }

        Map<String, Claim> claims = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            claims = jwt.getClaims();
        } catch (IllegalArgumentException var6) {
            log.warn("verifyJwt IllegalArgumentException: {}", var6.getMessage());
        } catch (JWTVerificationException var7) {
            log.warn("verifyJwt JWTVerificationException: {}", var7.getMessage());
        }

        return claims;
    }

//    public static void main(String[] args) {
//        Map<String, Claim> claimMap = verifyJwt("this_is_so_secret", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc3AyIiwidXNlcl9zaG9ydG5hbWUiOiJ6aGVuZ3poZW5nIiwidXNlcl9pZCI6ImNzcDI4ZWMzLTVhMTQtNGE0NS1hN2MxLTYwM2U2ZDQ5ZDAwMCIsImlzcyI6ImNzcDIiLCJwaG9uZV9udW1iZXIiOiIxMzIyNzcwNTAzMCIsInNob3J0X25hbWUiOiJ6aGVuZ3poZW5nIiwidXNlcl90b2tlbiI6Ijc5MThlOGY3ZmI2MDRmMGViMWNlYTZhMDQwMTZiNmMxIiwiZXhwaXJlZF9hdCI6IjE2MjU3MTg3MjciLCJleHAiOjE2MjU3NTExMjcsImVtYWlsIjoiIiwiaWFtX29wZW5pZCI6IjQ1Yzk3YzhiMDJiMTRhYTE5MGQyNGNkZDliYWY4NmJiIiwidXNlcm5hbWUiOiIxMzIyNzcwNTAzMCJ9.djyUHHm2vqDv7y5dCfTWqpS7agHXFWpIBS3_GHgcPEM");
//        System.out.println(claimMap.get("iam_openid").asString());
//    }

}
