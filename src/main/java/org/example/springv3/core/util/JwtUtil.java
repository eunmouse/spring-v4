package org.example.springv3.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.springv3.user.User;

import java.util.Date;

public class JwtUtil {

    public static String createSecret(User user) { // 테스트용
        String accessToken = JWT.create()
                .withSubject("바보")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000)) // 1초만에 만료
                .withClaim("id", user.getId()) // payload
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding"));
        return accessToken;
    }

    public static String createExp(User user) { // 테스트용
        String accessToken = JWT.create()
                .withSubject("바보")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000)) // 1초만에 만료
                .withClaim("id", user.getId()) // payload
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding"));
        return accessToken;
    }

    public static String create(User user) {
        String accessToken = JWT.create()
                .withSubject("바보")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("id", user.getId()) // payload
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding"));
        return accessToken;
    }

    // 검증코드
    public static User verify(String jwt){
        jwt = jwt.replace("Bearer ", ""); // Bearer 를 제거하고 토큰을 검증해야하니까, 아니면 검증이 안됨
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("metacoding")).build().verify(jwt);
        int id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();

        return User.builder()
                .id(id)
                .username(username)
                .build();
    }
}
