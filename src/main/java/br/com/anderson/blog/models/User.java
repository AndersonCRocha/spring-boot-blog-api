package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BasePersistent {

  private String fullName;
  private String email;
  private String password;
  private Boolean enabled = Boolean.TRUE;
  private Boolean blocked = Boolean.FALSE;

  public String getFullName() {
    return fullName;
  }
  public User setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getEmail() {
    return email;
  }
  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }
  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public Boolean getEnabled() {
    return enabled;
  }
  public User setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public Boolean getBlocked() {
    return blocked;
  }
  public User setBlocked(Boolean blocked) {
    this.blocked = blocked;
    return this;
  }

}
