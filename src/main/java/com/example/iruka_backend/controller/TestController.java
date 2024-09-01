package com.example.iruka_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.repository.UserRepository;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public Long addWord() {

    // POSTしたユーザーが誰かどうかはSpring Securityで管理されているので、
    // そのユーザーのユーザーIDを取得する。
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      username = ((UserDetails) authentication.getPrincipal()).getUsername();
    }
    // このusernameを元に、ユーザーIDを取得し、そのユーザーIDを使用してデータを処理します。
    UserEntity user = userRepository.findByEmail(username);
    System.out.println(user.getId());
    return user.getId();
  }
}
