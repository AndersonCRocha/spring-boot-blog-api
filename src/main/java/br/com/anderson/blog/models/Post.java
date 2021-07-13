package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends BasePersistent {

  private String content;
  private Image image;
  private User owner;

  public String getContent() {
    return content;
  }
  public Post setContent(String content) {
    this.content = content;
    return this;
  }

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  public Image getImage() {
    return image;
  }
  public Post setImage(Image image) {
    this.image = image;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public User getOwner() {
    return owner;
  }
  public Post setOwner(User owner) {
    this.owner = owner;
    return this;
  }

}
