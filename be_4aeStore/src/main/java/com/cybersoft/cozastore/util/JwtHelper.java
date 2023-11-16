package com.cybersoft.cozastore.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

    //Value giúp lấy thông tin cấu hình bên file application.properties
    @Value("${custom.token.key}")
    private String secKey;

    private long expiredTime = 8 * 60 * 60 * 1000; //8 hours to milliseconds

    public String generateToken(String data){

        //lấy key đã lưu trữ và sử dụng để tạo ra token
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secKey));

        //sinh ra thời gian hết hạn mới
        Date date = new Date();
        long newDateMilis = date.getTime() + expiredTime;
        Date newExpiredDate = new Date(newDateMilis);

        String token = Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .setExpiration(newExpiredDate)
                .compact();

        return token;
    }

    // Giải mã token
    public String parserToken (String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secKey));
        String data = Jwts.parserBuilder()
                .setSigningKey(key).build() //truyền key cần giải mã
                .parseClaimsJws(token) //truyền token cần giải mã
                .getBody().getSubject(); //lấy nội dung lưu trữ trong token

        return data;
    }

}
