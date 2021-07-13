package br.com.anderson.blog.services;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.models.UserRefreshToken;
import br.com.anderson.blog.utils.services.BaseCrudService;

import java.util.List;
import java.util.Optional;

public interface UserRefreshTokenService extends BaseCrudService<UserRefreshToken, Long> {

  String createRefreshToken(User user);
  List<UserRefreshToken> findByUser(User user);
  Optional<UserRefreshToken> findByUserAndToken(User user, String refreshToken);

}
