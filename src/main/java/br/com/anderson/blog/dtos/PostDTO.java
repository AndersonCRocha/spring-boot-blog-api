package br.com.anderson.blog.dtos;

import br.com.anderson.blog.models.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

  private Long id;
  private String content;
  private ImageDTO image;
  private UserDTO owner;
  private MultipartFile file;

  public PostDTO () { }

  public PostDTO(Post post, ImageDTO image) {
    this.id = post.getId();
    this.content = post.getContent();
    this.image = image;
    this.owner = new UserDTO(post.getOwner());
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

  public ImageDTO getImage() {
    return image;
  }
  public PostDTO setImage(ImageDTO image) {
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

  public MultipartFile getFile() {
    return file;
  }
  public PostDTO setFile(MultipartFile file) {
    this.file = file;
    return this;
  }

}
