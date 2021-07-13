package br.com.anderson.blog.controllers;

import br.com.anderson.blog.dtos.UserDTO;
import br.com.anderson.blog.security.utils.AuthenticationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final AuthenticationUtils authenticationUtils;

  public UserController(AuthenticationUtils authenticationUtils) {
    this.authenticationUtils = authenticationUtils;
  }

  @GetMapping("me")
  public UserDTO me() {
    return new UserDTO(this.authenticationUtils.getAuthenticatedUser());
  }

}
