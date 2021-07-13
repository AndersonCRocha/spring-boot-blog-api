package br.com.anderson.blog.dtos;

import javax.validation.constraints.NotBlank;

public class RefreshTokenDTO {

  private String refreshToken;

  @NotBlank
  public String getRefreshToken() {
    return refreshToken;
  }
  public RefreshTokenDTO setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

}
