package br.com.anderson.blog.repositories;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.models.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

  List<UserRefreshToken> findByUser(User user);
  Optional<UserRefreshToken> findByUserAndToken(User user, String token);

}
