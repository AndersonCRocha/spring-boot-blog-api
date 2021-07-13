package br.com.anderson.blog.security.service.impl;

import br.com.anderson.blog.dtos.JWTTokenDTO;
import br.com.anderson.blog.dtos.LoginDTO;
import br.com.anderson.blog.dtos.RefreshTokenDTO;
import br.com.anderson.blog.dtos.RegisterDTO;
import br.com.anderson.blog.dtos.UserDTO;
import br.com.anderson.blog.exceptions.InvalidRefreshTokenException;
import br.com.anderson.blog.exceptions.UserAlreadyExistsException;
import br.com.anderson.blog.models.User;
import br.com.anderson.blog.models.UserRefreshToken;
import br.com.anderson.blog.security.config.JWTConfigurationProperties;
import br.com.anderson.blog.security.exception.InvalidCredentials;
import br.com.anderson.blog.security.model.UserDetailsImpl;
import br.com.anderson.blog.security.service.AuthenticationService;
import br.com.anderson.blog.security.utils.JWTUtils;
import br.com.anderson.blog.services.UserRefreshTokenService;
import br.com.anderson.blog.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JWTUtils jwtUtils;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserRefreshTokenService userRefreshTokenService;
  private final JWTConfigurationProperties jwtConfigurationProperties;

  public AuthenticationServiceImpl(
    JWTUtils jwtUtils, UserService userService, PasswordEncoder passwordEncoder,
    UserRefreshTokenService userRefreshTokenService, JWTConfigurationProperties jwtConfigurationProperties
  ) {
    this.jwtUtils = jwtUtils;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.userRefreshTokenService = userRefreshTokenService;
    this.jwtConfigurationProperties = jwtConfigurationProperties;
  }

  @Override
  public JWTTokenDTO login(LoginDTO loginDTO) {
    final InvalidCredentials invalidCredentialsException = new InvalidCredentials("Invalid email and/or password");

    User user = this.userService.findByEmail(loginDTO.getEmail())
      .orElseThrow(() -> invalidCredentialsException);

    boolean passwordsMatch = this.passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
    if (!passwordsMatch) {
      throw invalidCredentialsException;
    }

    String refreshToken = this.userRefreshTokenService.createRefreshToken(user);
    LocalDateTime refreshTokenValidity =
      LocalDateTime.now().plus(this.jwtConfigurationProperties.getRefreshTokenValidity());

    return this.generateAccessTokenResponse(user, refreshToken, refreshTokenValidity);
  }

  @Override
  public JWTTokenDTO refresh(RefreshTokenDTO refreshTokenDTO) {
    String email = this.jwtUtils.getSubject(refreshTokenDTO.getRefreshToken());
    User user = this.userService.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found for email: %s".formatted(email)));

    Optional<UserRefreshToken> userRefreshTokenOptional =
      this.userRefreshTokenService.findByUserAndToken(user, refreshTokenDTO.getRefreshToken());

    return userRefreshTokenOptional
      .map(userRefreshToken -> {
        if (userRefreshToken.getValidity().isBefore(LocalDateTime.now())) {
          this.userRefreshTokenService.delete(userRefreshToken);
          throw new InvalidRefreshTokenException("Refresh token expired!");
        }

        return this.generateAccessTokenResponse(userRefreshToken);
      })
      .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token!"));
  }

  @Override
  public UserDTO register(RegisterDTO registerDTO) {
    this.userService.findByEmail(registerDTO.getEmail())
      .ifPresent(user -> {
        throw new UserAlreadyExistsException("User already registered with email %s".formatted(user.getEmail()));
      });

    User user = this.userService.save(
      new User()
        .setFullName(registerDTO.getFullName())
        .setEmail(registerDTO.getEmail())
        .setPassword(this.passwordEncoder.encode(registerDTO.getPassword()))
    );

    return new UserDTO(user);
  }

  private JWTTokenDTO generateAccessTokenResponse(UserRefreshToken userRefreshToken) {
    return this.generateAccessTokenResponse(
      userRefreshToken.getUser(), userRefreshToken.getToken(), userRefreshToken.getValidity()
    );
  }

  private JWTTokenDTO generateAccessTokenResponse(User user, String refreshToken, LocalDateTime refreshTokenValidity) {
    String authorities = new UserDetailsImpl(user).getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    String accessToken = this.jwtUtils.generateAccessToken(user.getEmail(), authorities, new HashMap<>());

    return new JWTTokenDTO()
      .setAccessToken(accessToken)
      .setAccessTokenExpiresIn(LocalDateTime.now().plus(this.jwtConfigurationProperties.getAccessTokenValidity()))
      .setRefreshToken(refreshToken)
      .setRefreshTokenExpiresIn(refreshTokenValidity);
  }

}
