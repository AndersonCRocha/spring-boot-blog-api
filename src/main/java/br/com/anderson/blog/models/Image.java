package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends BasePersistent {

  private byte[] content;
  private String mimeType;

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

}
