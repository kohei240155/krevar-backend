package com.example.iruka_backend.service;

import org.springframework.http.ResponseEntity;
import com.example.iruka_backend.requestdto.LoginRequest;
import com.example.iruka_backend.requestdto.SignUpRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    /**
     * ログインする
     *
     * @param loginRequest ログインリクエスト
     * @param session セッション
     * @return ログイン成功時のレスポンス
     */
    ResponseEntity<?> login(LoginRequest loginRequest, HttpSession session);

    /**
     * ユーザーを登録する
     *
     * @param signUpRequest ユーザー登録リクエスト
     * @return ユーザー登録成功時のレスポンス
     */
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
}
