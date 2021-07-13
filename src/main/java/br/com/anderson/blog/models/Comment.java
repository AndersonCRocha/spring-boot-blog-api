package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment extends BasePersistent {

  private String content;
  private Post post;
  private User owner;

  public String getContent() {
    return content;
  }
  public Comment setContent(String content) {
    this.content = content;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Post getPost() {
    return post;
  }
  public Comment setPost(Post post) {
    this.post = post;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public User getOwner() {
    return owner;
  }
  public Comment setOwner(User owner) {
    this.owner = owner;
    return this;
  }

}
