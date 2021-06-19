package com.example.blogserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;

import java.util.Date;
import java.util.HashMap;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/15
 */
public class JWTUtil {
    /**
     * 过期时间
     */
    private static final long EXP_TIME = 15 * 60 * 1000;
    /**
     * 盐值
     */
    private static final String SecretSalt = "eb875dce-9493-4013-9c33-07ab066f690d";

    /**
     * 根据账号生成token
     *
     * @param account
     * @return token
     */
    public static String createToken(String account) {
        Date date = new Date(System.currentTimeMillis() + EXP_TIME);
        HashMap header = new HashMap<>(2);
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Algorithm algorithm = Algorithm.HMAC256(SecretSalt);
        return JWT.create().withHeader(header).withClaim("account", account).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 解析token
     * @param token
     * @return 解析结果
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SecretSalt);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
