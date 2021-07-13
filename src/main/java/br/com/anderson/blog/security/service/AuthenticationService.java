package br.com.anderson.blog.security.service;

import br.com.anderson.blog.dtos.JWTTokenDTO;
import br.com.anderson.blog.dtos.LoginDTO;
import br.com.anderson.blog.dtos.RefreshTokenDTO;
import br.com.anderson.blog.dtos.RegisterDTO;
import br.com.anderson.blog.dtos.UserDTO;

public interface AuthenticationService {

  JWTTokenDTO login(LoginDTO loginDTO);
  JWTTokenDTO refresh(RefreshTokenDTO refreshTokenDTO);
  UserDTO register(RegisterDTO registerDTO);

}
