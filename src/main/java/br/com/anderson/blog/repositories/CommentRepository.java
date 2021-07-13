package br.com.anderson.blog.repositories;

import br.com.anderson.blog.models.Comment;
import br.com.anderson.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Page<Comment> findByPost(Post post, Pageable pageable);

}
