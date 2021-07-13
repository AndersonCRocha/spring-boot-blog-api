package br.com.anderson.blog.models;

import br.com.anderson.blog.utils.BasePersistent;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "albums")
public class Album extends BasePersistent {

  private String name;
  private User owner;
  private List<Image> photos = new ArrayList<>();

  public String getName() {
    return name;
  }
  public Album setName(String name) {
    this.name = name;
    return this;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public User getOwner() {
    return owner;
  }
  public Album setOwner(User owner) {
    this.owner = owner;
    return this;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "album", orphanRemoval = true)
  public List<Image> getPhotos() {
    return photos;
  }
  public Album setPhotos(List<Image> photos) {
    this.photos = photos;
    return this;
  }

  public Album addPhoto(Image photo) {
    this.photos.add(photo);
    return this;
  }
  public Album removePhoto(Long photoId) {
    this.photos.forEach(photo -> {
      if (Objects.nonNull(photoId) && photoId.equals(photo.getId())) {
        this.photos.remove(photo);
      }
    });

    return this;
  }
  public Optional<Image> getPhotoById(Long photoId) {
    return this.photos.stream()
      .filter(photo -> photo.getId().equals(photoId))
      .findFirst();
  }

}
