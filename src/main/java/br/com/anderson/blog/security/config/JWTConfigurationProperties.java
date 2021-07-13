package br.com.anderson.blog.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties("app.security.jwt")
public class JWTConfigurationProperties {

  private Duration accessTokenValidity;
  private Duration refreshTokenValidity;
  private String prefix;
  private String defaultId;
  private String secretKey;
  private String authoritiesKey;

  public Duration getAccessTokenValidity() {
    return accessTokenValidity;
  }
  public void setAccessTokenValidity(Duration accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public Duration getRefreshTokenValidity() {
    return refreshTokenValidity;
  }
  public void setRefreshTokenValidity(Duration refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public String getPrefix() {
    return prefix;
  }
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getDefaultId() {
    return defaultId;
  }
  public void setDefaultId(String defaultId) {
    this.defaultId = defaultId;
  }

  public String getSecretKey() {
    return secretKey;
  }
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getAuthoritiesKey() {
    return authoritiesKey;
  }
  public void setAuthoritiesKey(String authoritiesKey) {
    this.authoritiesKey = authoritiesKey;
  }

}