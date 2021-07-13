package br.com.anderson.blog.controllers;

import br.com.anderson.blog.dtos.JWTTokenDTO;
import br.com.anderson.blog.dtos.LoginDTO;
import br.com.anderson.blog.dtos.RefreshTokenDTO;
import br.com.anderson.blog.dtos.RegisterDTO;
import br.com.anderson.blog.dtos.UserDTO;
import br.com.anderson.blog.security.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

  private final AuthenticationService service;

  public AuthenticationController(AuthenticationService service) {
    this.service = service;
  }

  @PostMapping("login")
  public JWTTokenDTO login(@RequestBody LoginDTO loginDTO) {
    return this.service.login(loginDTO);
  }

  @PostMapping("refresh")
  public JWTTokenDTO refresh(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
    return this.service.refresh(refreshTokenDTO);
  }

  @PostMapping("register")
  public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO registerDTO, HttpServletRequest request) {
    UserDTO userDTO = this.service.register(registerDTO);
    URI uri = ServletUriComponentsBuilder
      .fromContextPath(request)
      .pathSegment("users/{id}")
      .buildAndExpand(userDTO.getId())
      .toUri();
    return ResponseEntity.created(uri).body(userDTO);
  }

}
