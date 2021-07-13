package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends BasePersistent {

  private String content;
  private byte[] image;
  private String imageMimeType;
  private User owner;

  public String getContent() {
    return content;
  }
  public Post setContent(String content) {
    this.content = content;
    return this;
  }

  public byte[] getImage() {
    return image;
  }
  public Post setImage(byte[] image) {
    this.image = image;
    return this;
  }

  public String getImageMimeType() {
    return imageMimeType;
  }
  public Post setImageMimeType(String imageMimeType) {
    this.imageMimeType = imageMimeType;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  public User getOwner() {
    return owner;
  }
  public Post setOwner(User owner) {
    this.owner = owner;
    return this;
  }

}
