package com.example.iruka_backend.requestdto;

public class SignUpRequest {
    private String email;
    private String password;

    // ゲッターとセッター
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}