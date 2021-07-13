package br.com.anderson.blog.services.impl;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.repositories.UserRepository;
import br.com.anderson.blog.services.UserService;
import br.com.anderson.blog.utils.services.impl.BaseCrudServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Long> implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  protected UserRepository getRepository() {
    return this.repository;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return this.getRepository().findByEmail(email);
  }
}
