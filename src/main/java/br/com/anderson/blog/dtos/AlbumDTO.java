package br.com.anderson.blog.dtos;

import br.com.anderson.blog.models.Album;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class AlbumDTO {

  private Long id;
  private String name;
  private List<ImageDTO> photos;

  public AlbumDTO() { }

  public AlbumDTO(Album album, List<ImageDTO> photos) {
    this.id = album.getId();
    this.name = album.getName();
    this.photos = photos;
  }

  public Long getId() {
    return id;
  }
  public AlbumDTO setId(Long id) {
    this.id = id;
    return this;
  }

  @NotBlank
  public String getName() {
    return name;
  }
  public AlbumDTO setName(String name) {
    this.name = name;
    return this;
  }

  public List<ImageDTO> getPhotos() {
    return photos;
  }
  public AlbumDTO setPhotos(List<ImageDTO> photos) {
    this.photos = photos;
    return this;
  }

}
