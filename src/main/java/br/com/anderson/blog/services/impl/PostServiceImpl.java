package br.com.anderson.blog.services.impl;

import br.com.anderson.blog.models.Post;
import br.com.anderson.blog.repositories.PostRepository;
import br.com.anderson.blog.security.utils.AuthenticationUtils;
import br.com.anderson.blog.services.PostService;
import br.com.anderson.blog.utils.PersistentUtils;
import br.com.anderson.blog.utils.services.impl.BaseCrudServiceImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends BaseCrudServiceImpl<Post, Long> implements PostService {

  private final PostRepository repository;
  private final AuthenticationUtils authenticationUtils;

  public PostServiceImpl(PostRepository repository, AuthenticationUtils authenticationUtils) {
    this.repository = repository;
    this.authenticationUtils = authenticationUtils;
  }

  @Override
  protected PostRepository getRepository() {
    return this.repository;
  }

  @Override
  public Post save(Post post) {
    post.setOwner(this.authenticationUtils.getAuthenticatedUser());
    return super.save(post);
  }

  @Override
  public void delete(Post post) {
    if (PersistentUtils.notEquals(post.getOwner(), this.authenticationUtils.getAuthenticatedUser())) {
      throw new AccessDeniedException("The post can only be deleted by its owner");
    }
    super.delete(post);
  }
}
