package com.example.krevar_backend.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration.ms}")
  private long jwtExpirationMs;

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  // JWTトークンを生成するメソッド
  public String generateToken(String email, String userId) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId); // ユーザーIDをString型としてクレームに追加
    return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret)),
            SignatureAlgorithm.HS512)
        .compact();
  }

  // トークンからユーザー名（メールアドレス）を取得するメソッド
  public String getEmailFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(Base64.getDecoder().decode(jwtSecret)) // デコードしたキーで署名を検証
        .build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  // トークンの有効性をチェックするメソッド
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(Base64.getDecoder().decode(jwtSecret)) // デコードしたキーで署名を検証
          .build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
      return false;
    }
  }

  // JWTトークンからユーザーIDを取得するメソッド
  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))).build()
        .parseClaimsJws(token).getBody();
    String userIdString = claims.get("userId", String.class); // クレームからユーザーIDをString型で取得
    return Long.parseLong(userIdString); // String型をLong型に変換
  }
}
