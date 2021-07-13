package br.com.anderson.blog.dtos;

import br.com.anderson.blog.models.Comment;

public class CommentDTO {

  private Long id;
  private String content;
  private PostDTO post;
  private UserDTO owner;

  public CommentDTO() { }

  public CommentDTO(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.post = new PostDTO(comment.getPost(), null);
    this.owner = new UserDTO(comment.getOwner());
  }

  public Long getId() {
    return id;
  }
  public CommentDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getContent() {
    return content;
  }
  public CommentDTO setContent(String content) {
    this.content = content;
    return this;
  }

  public PostDTO getPost() {
    return post;
  }
  public CommentDTO setPost(PostDTO post) {
    this.post = post;
    return this;
  }

  public UserDTO getOwner() {
    return owner;
  }
  public CommentDTO setOwner(UserDTO owner) {
    this.owner = owner;
    return this;
  }

}
