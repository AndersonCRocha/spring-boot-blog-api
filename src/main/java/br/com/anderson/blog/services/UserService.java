package br.com.anderson.blog.services;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.utils.services.BaseCrudService;

import java.util.Optional;

public interface UserService extends BaseCrudService<User, Long> {

  Optional<User> findByEmail(String email);

}
