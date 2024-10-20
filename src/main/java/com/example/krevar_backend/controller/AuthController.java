package com.example.krevar_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.krevar_backend.requestdto.GoogleLoginRequest;
import com.example.krevar_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  /**
   * Googleログインを処理する
   *
   * @param payload Googleログインのペイロード
   * @return Googleログイン成功時のレスポンス
   */
  @PostMapping("/google-login")
  public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {

    logger.info("------------- GoogleログインAPI開始 -------------");

    ResponseEntity<?> response = authService.googleLogin(request);

    logger.info("------------- GoogleログインAPI終了 -------------");

    return response;
  }

  /**
   * ログアウトを処理する
   *
   * @param session セッション
   * @return ログアウト成功時のレスポンス
   */
  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpSession session) {

    logger.info("------------- ログアウトAPI開始 -------------");

    authService.logout(session);

    logger.info("------------- ログアウトAPI終了 -------------");

    return ResponseEntity.ok("Logout successful");
  }
}
