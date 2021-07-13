package br.com.anderson.blog.controllers;

import br.com.anderson.blog.dtos.CommentDTO;
import br.com.anderson.blog.models.Comment;
import br.com.anderson.blog.models.Post;
import br.com.anderson.blog.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("comments")
public class CommentController {

  private final CommentService service;

  public CommentController(CommentService service) {
    this.service = service;
  }

  @GetMapping("{id}")
  public CommentDTO find(@PathVariable Long id) {
    return new CommentDTO(this.service.findOrFailById(id));
  }

  @GetMapping
  public Page<CommentDTO> list(@PageableDefault Pageable pageable) {
    return this.service.findAll(pageable).map(CommentDTO::new);
  }

  @PostMapping
  public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO commentDTO) throws IOException {
    Post post = new Post();
    post.setId(commentDTO.getPost().getId());

    Comment savedComment = this.service.save(
      new Comment()
        .setContent(commentDTO.getContent())
        .setPost(post)
    );

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .pathSegment("{id}")
      .buildAndExpand(savedComment.getId())
      .toUri();
    return ResponseEntity.created(uri).body(new CommentDTO(savedComment));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Comment comment = this.service.findOrFailById(id);
    this.service.delete(comment);
    return ResponseEntity.noContent().build();
  }

}
