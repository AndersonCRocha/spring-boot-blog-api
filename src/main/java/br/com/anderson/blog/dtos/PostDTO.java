package br.com.anderson.blog.dtos;

import br.com.anderson.blog.models.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

  private Long id;
  private String content;
  private MultipartFile image;
  private UserDTO owner;
  private URI imageUri;

  public PostDTO() { }

  public PostDTO(Post post, URI imageUri) {
    this.id = post.getId();
    this.content = post.getContent();
    this.owner = new UserDTO(post.getOwner());
    this.imageUri = imageUri;
  }

  public Long getId() {
    return id;
  }
  public PostDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getContent() {
    return content;
  }
  public PostDTO setContent(String content) {
    this.content = content;
    return this;
  }

  public MultipartFile getImage() {
    return image;
  }
  public PostDTO setImage(MultipartFile image) {
    this.image = image;
    return this;
  }

  public UserDTO getOwner() {
    return owner;
  }
  public PostDTO setOwner(UserDTO owner) {
    this.owner = owner;
    return this;
  }

  public URI getImageUri() {
    return imageUri;
  }
  public PostDTO setImageUri(URI imageUri) {
    this.imageUri = imageUri;
    return this;
  }
}
