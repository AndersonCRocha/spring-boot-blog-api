package br.com.anderson.blog.dtos;

public class RegisterDTO {

  private String fullName;
  private String email;
  private String password;

  public String getFullName() {
    return fullName;
  }
  public RegisterDTO setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getEmail() {
    return email;
  }
  public RegisterDTO setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }
  public RegisterDTO setPassword(String password) {
    this.password = password;
    return this;
  }

}
