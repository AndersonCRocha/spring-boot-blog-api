package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_refresh_token")
public class UserRefreshToken extends BasePersistent {

  private User user;
  private String token;
  private LocalDateTime validity;

  @ManyToOne(fetch = FetchType.LAZY)
  public User getUser() {
    return user;
  }
  public UserRefreshToken setUser(User user) {
    this.user = user;
    return this;
  }

  public String getToken() {
    return token;
  }
  public UserRefreshToken setToken(String token) {
    this.token = token;
    return this;
  }

  public LocalDateTime getValidity() {
    return validity;
  }
  public UserRefreshToken setValidity(LocalDateTime validity) {
    this.validity = validity;
    return this;
  }

}
