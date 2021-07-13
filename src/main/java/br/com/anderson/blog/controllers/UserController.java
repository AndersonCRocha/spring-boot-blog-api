package br.com.anderson.blog.controllers;

import br.com.anderson.blog.security.model.UserDetailsImpl;
import br.com.anderson.blog.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("me")
  public String me(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return userDetails.getUsername();
  }

}
