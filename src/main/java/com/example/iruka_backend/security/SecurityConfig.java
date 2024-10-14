package com.example.iruka_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.iruka_backend.util.JwtAuthenticationEntryPoint;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // カスタムエントリーポイントを追加

  @Value("${spring.mvc.cors.allowed-origins}")
  private String[] allowedOrigins;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authenticationManager) throws Exception {
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // セッションをステートレスにする
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS設定を追加
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll() // 認証なしでアクセス可能
            .requestMatchers("/webhook").permitAll() // StripeのWebhookは認証なしでアクセス可能
            .requestMatchers("/api/checkout/**").permitAll() // StripeのCheckoutは認証なしでアクセス可能
            .requestMatchers("/api/sample/**").permitAll() // サンプルAPIは認証なしでアクセス可能
            .anyRequest().authenticated() // それ以外は認証が必要
        )
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // カスタムエントリーポイントを設定
        .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtTokenProvider)); // JWTフィルタの追加

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // CORSの設定
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 必要なHTTPメソッドを指定
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 必要なヘッダーを指定
    configuration.setAllowCredentials(true); // クッキーの送信を許可
    configuration.setMaxAge(3600L); // CORSプリフライトリクエストのキャッシュ時間（秒）

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
