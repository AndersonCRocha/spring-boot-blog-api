package br.com.anderson.blog.security.filter;

import br.com.anderson.blog.security.config.JWTConfigurationProperties;
import br.com.anderson.blog.security.utils.JWTAuthoritiesConstants;
import br.com.anderson.blog.security.utils.JWTUtils;
import br.com.anderson.blog.utils.ApplicationContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationFilter extends OncePerRequestFilter {

  private JWTUtils jwtUtils;
  private UserDetailsService userDetailsService;
  private JWTConfigurationProperties jwtConfigurationProperties;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    String token = this.extractTokenFromHeader(request).orElse("");

    boolean isValidToken = StringUtils.isNotBlank(token)
      && this.getJwtUtils().isValid(token)
      && this.hasAccessTokenPermission(token);

    if (isValidToken) {
      String email = this.getJwtUtils().getClaims(token).getSubject();
      UserDetails user = this.getUserDetailsService().loadUserByUsername(email);

      UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean hasAccessTokenPermission(String token) {
    return this.getJwtUtils().getAuthorities(token).contains(JWTAuthoritiesConstants.ACCESS_TOKEN);
  }

  private Optional<String> extractTokenFromHeader(HttpServletRequest request) {
    return Optional.ofNullable(
      this.getJwtUtils().stripBearerToken(request.getHeader(HttpHeaders.AUTHORIZATION))
    );
  }

  public JWTUtils getJwtUtils() {
    if (Objects.isNull(this.jwtUtils)) {
      this.jwtUtils = ApplicationContextUtils.getBean(JWTUtils.class);
    }
    return jwtUtils;
  }

  public UserDetailsService getUserDetailsService() {
    if (Objects.isNull(this.userDetailsService)) {
      this.userDetailsService = ApplicationContextUtils.getBean(UserDetailsService.class);
    }
    return userDetailsService;
  }

  public JWTConfigurationProperties getJwtConfigurationProperties() {
    if (Objects.isNull(this.jwtConfigurationProperties)) {
      this.jwtConfigurationProperties = ApplicationContextUtils.getBean(JWTConfigurationProperties.class);
    }
    return jwtConfigurationProperties;
  }

}