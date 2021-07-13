package br.com.anderson.blog.security.utils;

import br.com.anderson.blog.models.User;
import br.com.anderson.blog.security.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() instanceof UserDetailsImpl) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }

    return null;
  }

}
