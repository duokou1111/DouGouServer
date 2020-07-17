package com.springboot.wzh.utils;

import com.springboot.wzh.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String SUBJECT = "DouGou";

    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;

    public static final String APPSECRET_KEY = "WuZihan";

    private static final String ROLE_CLAIMS = "rol";

    private static final String ISSUER = "admin";

    public static String generateJsonWebToken(UserInfo user) {

        if (user.getId() == null || user.getUserName() == null) {
            return null;
        }

        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, "rol");
        String token = Jwts
                .builder()
                .setSubject(SUBJECT)
                .setClaims(map)
                .claim("id", user.getId())
                .claim("username", user.getUserName())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setIssuer("admin")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }


    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public static String getUserRole(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public static void main(String[] args) {
        String name = "acong";
        String role = "rol";
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setRole(1);
        userInfo.setUserName("123");
        String token = generateJsonWebToken(userInfo);
        System.out.println(token);
        //Claims claims = checkJWT(token);
    }



}
