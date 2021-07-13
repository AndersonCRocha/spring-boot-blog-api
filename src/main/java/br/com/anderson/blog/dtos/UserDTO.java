package br.com.anderson.blog.dtos;

import br.com.anderson.blog.models.User;

public class UserDTO {

  private Long id;
  private String fullName;
  private String email;
  private Boolean enabled;
  private Boolean blocked;

  public UserDTO(User user) {
    this.id = user.getId();
    this.fullName = user.getFullName();
    this.email = user.getEmail();
    this.enabled = user.getEnabled();
    this.blocked = user.getEnabled();
  }

  public Long getId() {
    return id;
  }
  public UserDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getFullName() {
    return fullName;
  }
  public UserDTO setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getEmail() {
    return email;
  }
  public UserDTO setEmail(String email) {
    this.email = email;
    return this;
  }

  public Boolean getEnabled() {
    return enabled;
  }
  public UserDTO setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public Boolean getBlocked() {
    return blocked;
  }
  public UserDTO setBlocked(Boolean blocked) {
    this.blocked = blocked;
    return this;
  }

}
