package br.com.anderson.blog.dtos;

import javax.validation.constraints.NotBlank;

public class RegisterDTO {

  private String fullName;
  private String email;
  private String password;

  @NotBlank
  public String getFullName() {
    return fullName;
  }
  public RegisterDTO setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  @NotBlank
  public String getEmail() {
    return email;
  }
  public RegisterDTO setEmail(String email) {
    this.email = email;
    return this;
  }

  @NotBlank
  public String getPassword() {
    return password;
  }
  public RegisterDTO setPassword(String password) {
    this.password = password;
    return this;
  }

}
