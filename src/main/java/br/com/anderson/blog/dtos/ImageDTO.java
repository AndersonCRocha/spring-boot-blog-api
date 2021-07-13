package br.com.anderson.blog.dtos;

import java.net.URI;

public class ImageDTO {

  private Long id;
  private URI uri;

  public ImageDTO(Long id, URI uri) {
    this.id = id;
    this.uri = uri;
  }

  public Long getId() {
    return id;
  }
  public ImageDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public URI getUri() {
    return uri;
  }
  public ImageDTO setUri(URI uri) {
    this.uri = uri;
    return this;
  }

}
