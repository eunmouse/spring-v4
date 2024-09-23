package org.example.springv3.core.util;

import org.example.springv3.user.User;
import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    @Test
    public void createSecret_test(){
        User user = User.builder().id(1).username("ssar").build();

        String accessToken = JwtUtil.createSecret(user);
        System.out.println(accessToken);
    }

    @Test
    public void createExp_test(){
        User user = User.builder().id(1).username("ssar").build();

        String accessToken = JwtUtil.createExp(user);
        System.out.println(accessToken);
    }

    @Test
    public void create_test(){
        User user = User.builder().id(1).username("ssar").build();

        String accessToken = JwtUtil.create(user);
        System.out.println(accessToken);
    }

    // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLrsJTrs7QiLCJpZCI6MSwiZXhwIjoxNzI3NDAxMDc4fQ.1Ajxs0WTdg40m1CNloFCty1adboo5FpE7qMS0-IfICKFO10xO0u_0dn5R-Cfb9kNdramh15HxGg4kyC7cadJfw
    @Test
    public void verify_test(){
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLrsJTrs7QiLCJpZCI6MSwiZXhwIjoxNzI3NDE0Mjk2LCJ1c2VybmFtZSI6InNzYXIifQ.gsNDUQh296E_kgbIXIntTaD_IXHethRmANZB0w57chdjN1ntv9NXBgENkcDcXNCeu_cNl9QMbkVuYK0cifYzGQ";
        User user = JwtUtil.verify(accessToken);
        System.out.println(user.getId());
        System.out.println(user.getUsername());
    }
}
