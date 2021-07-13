package br.com.anderson.blog.dtos;

public class RefreshTokenDTO {

  private String refreshToken;

  public String getRefreshToken() {
    return refreshToken;
  }
  public RefreshTokenDTO setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

}
