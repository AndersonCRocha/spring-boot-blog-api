package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends BasePersistent {

  private byte[] content;
  private String mimeType;
  private Album album;

  public byte[] getContent() {
    return content;
  }
  public Image setContent(byte[] content) {
    this.content = content;
    return this;
  }

  public String getMimeType() {
    return mimeType;
  }
  public Image setMimeType(String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Album getAlbum() {
    return album;
  }
  public Image setAlbum(Album album) {
    this.album = album;
    return this;
  }

}
