package br.com.anderson.blog.security.utils;
import br.com.anderson.blog.security.config.JWTConfigurationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

  private final JWTConfigurationProperties jwtConfigurationProperties;

  public JWTUtils(JWTConfigurationProperties jwtConfigurationProperties) {
    this.jwtConfigurationProperties = jwtConfigurationProperties;
  }

  @SuppressWarnings("unchecked")
  public List<String> getAuthorities(String token) {
    return (List<String>) this.getClaims(token).get(jwtConfigurationProperties.getAuthoritiesKey());
  }

  public String getSubject(String token) {
    return this.getClaims(token).getSubject();
  }

  public String generateAccessToken(String subject, String authorities, Map<String, Object> claims) {
    if (!authorities.contains(JWTAuthoritiesConstants.ACCESS_TOKEN)) {
      authorities = authorities.concat(",").concat(JWTAuthoritiesConstants.ACCESS_TOKEN);
    }

    return this.generateToken(
      subject,
      new Date(jwtConfigurationProperties.getAccessTokenValidity().toMillis()),
      authorities,
      claims
    );
  }

  public String generateRefreshToken(String subject) {
    return this.generateToken(
      subject,
      new Date(jwtConfigurationProperties.getRefreshTokenValidity().toMillis()),
      JWTAuthoritiesConstants.REFRESH_TOKEN,
      new HashMap<>()
    );
  }

  public String stripBearerToken(String token) {
    return StringUtils.isNotBlank(token) && token.startsWith(this.jwtConfigurationProperties.getPrefix())
       ? token.replaceAll(this.jwtConfigurationProperties.getPrefix(), "").trim()
       : null;
  }

  private String generateToken(
    String subject, Date validity, String authorities, Map<String, Object> claims
  ) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    Date now = new Date();

    String token = Jwts
      .builder()
      .setClaims(new DefaultClaims(claims))
      .claim(jwtConfigurationProperties.getAuthoritiesKey(),
        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
      .setId(jwtConfigurationProperties.getDefaultId())
      .setSubject(subject)
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + validity.getTime()))
      .signWith(SignatureAlgorithm.HS512, jwtConfigurationProperties.getSecretKey().getBytes())
      .compact();

    return jwtConfigurationProperties.getPrefix() + " " + token;
  }

  public boolean isValid(String token) {
    boolean isValid = false;
    try {
      Date expiration = this.getClaims(token).getExpiration();
      isValid = expiration != null && !expiration.before(new Date());
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return isValid;
  }

  public Claims getClaims(String jwtToken) {
    String token = jwtToken.startsWith(this.jwtConfigurationProperties.getPrefix())
      ? this.stripBearerToken(jwtToken)
      : jwtToken;

    return Jwts.parser()
      .setSigningKey(jwtConfigurationProperties.getSecretKey().getBytes())
      .parseClaimsJws(token)
      .getBody();
  }

}
