package br.com.anderson.blog.services.impl;

import br.com.anderson.blog.models.Comment;
import br.com.anderson.blog.models.Post;
import br.com.anderson.blog.repositories.CommentRepository;
import br.com.anderson.blog.security.utils.AuthenticationUtils;
import br.com.anderson.blog.services.CommentService;
import br.com.anderson.blog.services.PostService;
import br.com.anderson.blog.utils.PersistentUtils;
import br.com.anderson.blog.utils.services.impl.BaseCrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseCrudServiceImpl<Comment, Long> implements CommentService {

  private final CommentRepository repository;
  private final AuthenticationUtils authenticationUtils;
  private final PostService postService;

  public CommentServiceImpl(
    CommentRepository repository, AuthenticationUtils authenticationUtils, PostService postService
  ) {
    this.repository = repository;
    this.authenticationUtils = authenticationUtils;
    this.postService = postService;
  }

  @Override
  protected CommentRepository getRepository() {
    return this.repository;
  }

  @Override
  public Comment save(Comment comment) {
    Long postId = comment.getPost().getId();
    Post post = this.postService.findById(postId).orElseThrow(
      () -> new IllegalArgumentException("Post not found for id: %s".formatted(postId))
    );

    comment
      .setPost(post)
      .setOwner(this.authenticationUtils.getAuthenticatedUser());
    return super.save(comment);
  }

  @Override
  public void delete(Comment comment) {
    if (PersistentUtils.notEquals(comment.getOwner(), this.authenticationUtils.getAuthenticatedUser())) {
      throw new AccessDeniedException("The comment can only be deleted by its owner");
    }
    super.delete(comment);
  }

  @Override
  public Page<Comment> findByPost(Post post, Pageable pageable) {
    return this.repository.findByPost(post, pageable);
  }

}
