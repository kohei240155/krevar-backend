package com.example.iruka_backend.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.LoginRequest;
import com.example.iruka_backend.requestdto.SignUpRequest;
import com.example.iruka_backend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * ログインする
     *
     * @param loginRequest ログインリクエスト
     * @param session セッション
     * @return ログイン成功時のレスポンス
     */
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest, HttpSession session) {

        logger.info("Login request received for email: {}", loginRequest.getEmail());

        try {
            // ログイン認証
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            // 認証情報をセッションに保存
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // セッションにユーザー情報を保存
            session.setAttribute("user", loginRequest.getEmail());

            logger.info("Login successful for email: {}", loginRequest.getEmail());

            // ユーザー情報を取得
            UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

            // ログイン成功時のレスポンスを作成
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());

            // ログイン成功時のレスポンスを返す
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // ログイン失敗時のエラーログを出力
            logger.error("Invalid credentials for email: {}", loginRequest.getEmail());
            // ログイン失敗時のレスポンスを返す
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /**
     * ユーザーを登録する
     *
     * @param signUpRequest ユーザー登録リクエスト
     * @return ユーザー登録成功時のレスポンス
     */
    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {

        // メールアドレスが既に登録されているかチェック
        if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {
            logger.error("メールアドレスが既に登録されています: {}", signUpRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken");
        }

        // ユーザーを新規登録
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("USER");

        // ユーザーを保存
        userRepository.save(user);

        // ユーザー登録成功時のレスポンスを返す
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    /**
     * Googleログインを処理する
     *
     * @param payload Googleログインのペイロード
     * @param request HTTPリクエスト
     * @return Googleログイン成功時のレスポンス
     */
    @Override
    public ResponseEntity<?> googleLogin(Map<String, Object> payload, HttpServletRequest request) {
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
