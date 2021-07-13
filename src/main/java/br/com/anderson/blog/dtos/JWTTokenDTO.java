package br.com.anderson.blog.dtos;

import java.time.LocalDateTime;

public class JWTTokenDTO {

  private String accessToken;
  private String refreshToken;
  private LocalDateTime accessTokenExpiresIn;
  private LocalDateTime refreshTokenExpiresIn;

  public String getAccessToken() {
    return accessToken;
  }
  public JWTTokenDTO setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public String getRefreshToken() {
    return refreshToken;
  }
  public JWTTokenDTO setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  public LocalDateTime getAccessTokenExpiresIn() {
    return accessTokenExpiresIn;
  }
  public JWTTokenDTO setAccessTokenExpiresIn(LocalDateTime accessTokenExpiresIn) {
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    return this;
  }

  public LocalDateTime getRefreshTokenExpiresIn() {
    return refreshTokenExpiresIn;
  }
  public JWTTokenDTO setRefreshTokenExpiresIn(LocalDateTime refreshTokenExpiresIn) {
    this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    return this;
  }

}
