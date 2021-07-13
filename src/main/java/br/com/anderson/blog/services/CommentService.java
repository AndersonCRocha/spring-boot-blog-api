package br.com.anderson.blog.services;

import br.com.anderson.blog.models.Comment;
import br.com.anderson.blog.models.Post;
import br.com.anderson.blog.utils.services.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends BaseCrudService<Comment, Long> {

  Page<Comment> findByPost(Post post, Pageable pageable);

}
