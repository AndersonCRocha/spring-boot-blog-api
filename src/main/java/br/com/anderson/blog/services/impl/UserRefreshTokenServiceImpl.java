package br.com.anderson.blog.services.impl;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.models.UserRefreshToken;
import br.com.anderson.blog.repositories.UserRefreshTokenRepository;
import br.com.anderson.blog.security.config.JWTConfigurationProperties;
import br.com.anderson.blog.security.utils.JWTUtils;
import br.com.anderson.blog.services.UserRefreshTokenService;
import br.com.anderson.blog.utils.services.impl.BaseCrudServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserRefreshTokenServiceImpl
  extends BaseCrudServiceImpl<UserRefreshToken, Long> implements UserRefreshTokenService {

  private final UserRefreshTokenRepository repository;
  private final JWTConfigurationProperties jwtConfigurationProperties;
  private final JWTUtils jwtUtils;

  public UserRefreshTokenServiceImpl(
    UserRefreshTokenRepository repository, JWTConfigurationProperties jwtConfigurationProperties, JWTUtils jwtUtils
  ) {
    this.repository = repository;
    this.jwtConfigurationProperties = jwtConfigurationProperties;
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected JpaRepository<UserRefreshToken, Long> getRepository() {
    return this.repository;
  }

  @Override
  public String createRefreshToken(User user) {
    this.deleteExpired(user);

    String refreshToken = this.jwtUtils.generateRefreshToken(user.getEmail());

    UserRefreshToken userRefreshToken = new UserRefreshToken()
      .setUser(user)
      .setToken(refreshToken)
      .setValidity(LocalDateTime.now().plus(this.jwtConfigurationProperties.getRefreshTokenValidity()));

    this.repository.save(userRefreshToken);

    return refreshToken;
  }

  @Override
  public List<UserRefreshToken> findByUser(User user) {
    return this.repository.findByUser(user);
  }

  @Override
  public Optional<UserRefreshToken> findByUserAndToken(User user, String refreshToken) {
    return this.repository.findByUserAndToken(user, refreshToken);
  }

  private void deleteExpired(User user) {
    List<UserRefreshToken> userRefreshTokenList = this.findByUser(user);
    userRefreshTokenList.stream()
      .filter(userRefreshToken -> userRefreshToken.getValidity().isBefore(LocalDateTime.now()))
      .forEach(this::delete);
  }

}
