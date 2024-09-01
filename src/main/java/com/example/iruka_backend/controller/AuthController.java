package com.example.iruka_backend.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.LoginRequest;
import com.example.iruka_backend.requestdto.SignUpRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {

    // HttpSessionを取得
    HttpSession session = request.getSession();

    logger.info("Login request received for email: {}", loginRequest.getEmail());

    try {

      // ユーザーが入力した認証情報が正しいかどうかを確認
      Authentication authentication =
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(), loginRequest.getPassword()));

      // セキュリティコンテキストに認証情報を設定
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // セッションにユーザー情報を保存
      session.setAttribute("user", loginRequest.getEmail());
      logger.info("Login successful for email: {}", loginRequest.getEmail());

      // ユーザー情報を取得
      UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

      // フロントエンドに返す情報を作成
      Map<String, Object> response = new HashMap<>();
      response.put("id", user.getId());
      response.put("email", user.getEmail());
      response.put("role", user.getRole());

      return ResponseEntity.ok(response);

    } catch (BadCredentialsException e) {

      logger.error("Invalid credentials for email: {}", loginRequest.getEmail());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
    if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken");
    }

    UserEntity user = new UserEntity();
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    user.setRole("USER");

    userRepository.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }

  @PostMapping("/google-login")
  public ResponseEntity<?> googleLogin(@RequestBody Map<String, Object> payload,
      HttpServletRequest request) {
    String email = (String) payload.get("email");
    String googleId = (String) payload.get("googleId");

    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      // ユーザーが存在しない場合、新規登録
      user = new UserEntity();
      user.setEmail(email);
      user.setRole("USER");
      user.setPassword("default_password"); // デフォルトのパスワードを設定
      user.setGoogleId(googleId); // Google IDを設定
      userRepository.save(user);
    }

    // セッションを作成して認証情報を設定
    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    HttpSession session = request.getSession();
    session.setAttribute("user", email);

    logger.info("Google login successful for email: {}", email);

    Map<String, Object> response = new HashMap<>();
    response.put("id", user.getId());
    response.put("email", user.getEmail());
    response.put("role", user.getRole());

    return ResponseEntity.ok(response);
  }
}
