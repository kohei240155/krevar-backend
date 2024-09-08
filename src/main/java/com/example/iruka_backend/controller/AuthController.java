package com.example.iruka_backend.controller;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.requestdto.LoginRequest;
import com.example.iruka_backend.requestdto.SignUpRequest;
import com.example.iruka_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

  @Autowired
  private AuthService authService;

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  /**
   * ログインする
   *
   * @param loginRequest ログインリクエスト
   * @param request HTTPリクエスト
   * @return ログイン成功時のレスポンス
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {

    logger.info("------------- ログインAPI開始 -------------");

    // HttpSessionを取得
    HttpSession session = request.getSession();

    ResponseEntity<?> response = authService.login(loginRequest, session);

    logger.info("------------- ログインAPI終了 -------------");

    return response;
  }

  /**
   * ユーザーを登録する
   *
   * @param signUpRequest ユーザー登録リクエスト
   * @return ユーザー登録成功時のレスポンス
   */
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

    logger.info("------------- ユーザー登録API開始 -------------");

    ResponseEntity<?> response = authService.registerUser(signUpRequest);

    logger.info("------------- ユーザー登録API終了 -------------");

    return response;
  }

  /**
   * Googleログインを処理する
   *
   * @param payload Googleログインのペイロード
   * @param request HTTPリクエスト
   * @return Googleログイン成功時のレスポンス
   */
  @PostMapping("/google-login")
  public ResponseEntity<?> googleLogin(@RequestBody Map<String, Object> payload,
      HttpServletRequest request) {

    logger.info("------------- GoogleログインAPI開始 -------------");

    ResponseEntity<?> response = authService.googleLogin(payload, request);

    logger.info("------------- GoogleログインAPI終了 -------------");

    return response;
  }
}
