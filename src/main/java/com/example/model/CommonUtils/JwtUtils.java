package com.example.model.CommonUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 */
public class JwtUtils {

    /**
     * TOKEN的有效期1小时（S）
     */
    private static final int TOKEN_TIME_OUT = 1 * 3600;

    /**
     * 加密KEY
     */
    private static final String TOKEN_SECRET = "itcast";


    /**
     * 生成Token
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getToken(Map params) {
        long currentTime = System.currentTimeMillis();
        try {
            return Jwts.builder()
                    //加密方式与秘钥
                    .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(TOKEN_SECRET.getBytes("UTF-8")))
                    //过期时间戳
                    .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))
                    .addClaims(params)
                    .compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取Token中的claims信息
     */
    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encode(TOKEN_SECRET.getBytes("UTF-8")))
                    .parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 是否有效 true-有效，false-失效
     */
    public static boolean verifyToken(String token) {

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encode(TOKEN_SECRET.getBytes("UTF-8")))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}