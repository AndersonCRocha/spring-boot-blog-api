package br.com.anderson.blog.controllers;

import br.com.anderson.blog.dtos.CommentDTO;
import br.com.anderson.blog.dtos.ImageDTO;
import br.com.anderson.blog.dtos.PostDTO;
import br.com.anderson.blog.models.Image;
import br.com.anderson.blog.models.Post;
import br.com.anderson.blog.services.CommentService;
import br.com.anderson.blog.services.PostService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Objects;

@RestController
@RequestMapping("posts")
public class PostController {

  private final PostService service;
  private final CommentService commentService;

  public PostController(PostService service, CommentService commentService) {
    this.service = service;
    this.commentService = commentService;
  }

  @GetMapping("{id}")
  public PostDTO find(@PathVariable Long id) {
    Post post = this.service.findOrFailById(id);
    ImageDTO imageDTO = this.extractImageDTOFromSavedPost(post);
    return new PostDTO(post, imageDTO);
  }

  @GetMapping
  public Page<PostDTO> list(@PageableDefault Pageable pageable) {
    return this.service.findAll(pageable)
      .map(post -> new PostDTO(post, this.extractImageDTOFromSavedPost(post)));
  }

  @PostMapping
  public ResponseEntity<PostDTO> create(@ModelAttribute PostDTO postDTO) throws IOException {
    MultipartFile file = postDTO.getFile();
    Image image = null;
    if (Objects.nonNull(file)) {
      image = new Image()
        .setContent(file.getBytes())
        .setMimeType(file.getContentType());
    }

    Post savedPost = this.service.save(
      new Post()
        .setContent(postDTO.getContent())
        .setImage(image)
    );

    ImageDTO imageDTO = this.extractImageDTOFromSavedPost(savedPost);

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .pathSegment("{id}")
      .buildAndExpand(savedPost.getId())
      .toUri();
    return ResponseEntity.created(uri).body(new PostDTO(savedPost, imageDTO));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Post post = this.service.findOrFailById(id);
    this.service.delete(post);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("{id}/image")
  protected ResponseEntity<Resource> getImage(@PathVariable Long id) {
    Post post = this.service.findOrFailById(id);
    Image image = post.getImage();

    if (Objects.isNull(image)) {
      throw new EntityNotFoundException("Not found image for post with id: %s".formatted(id));
    }

    String fileName = MessageFormat.format("post-image-{0}", post.getId());

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_TYPE, image.getMimeType())
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(fileName))
      .body(new ByteArrayResource(image.getContent()));
  }

  @GetMapping("{id}/comments")
  protected Page<CommentDTO> listComments(@PathVariable Long id, @PageableDefault Pageable pageable) {
    Post post = this.service.findOrFailById(id);
    return this.commentService.findByPost(post, pageable).map(CommentDTO::new);
  }

  private ImageDTO extractImageDTOFromSavedPost(Post post) {
    if (Objects.isNull(post.getImage())) {
      return null;
    }

    URI imageUri = ServletUriComponentsBuilder
      .fromCurrentContextPath()
      .pathSegment("posts/{id}/image")
      .buildAndExpand(post.getId())
      .toUri();

    return new ImageDTO(post.getImage().getId(), imageUri);
  }

}
