package br.com.anderson.blog.controllers;

import br.com.anderson.blog.dtos.AlbumDTO;
import br.com.anderson.blog.dtos.ImageDTO;
import br.com.anderson.blog.models.Album;
import br.com.anderson.blog.models.Image;
import br.com.anderson.blog.services.AlbumService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RestController
@RequestMapping("albums")
public class AlbumController {
  private final BiFunction<Long, Long, URI> generatePhotoURI = (albumId, photoId) -> ServletUriComponentsBuilder
    .fromCurrentContextPath()
    .pathSegment("albums/{albumId}/photos/{photoId}")
    .buildAndExpand(albumId, photoId)
    .toUri();

  private final AlbumService service;

  public AlbumController(AlbumService service) {
    this.service = service;
  }

  @GetMapping("{id}")
  public AlbumDTO find(@PathVariable Long id) {
    Album album = this.service.findOrFailById(id);
    return new AlbumDTO(album, null);
  }

  @GetMapping
  public Page<AlbumDTO> list(@PageableDefault Pageable pageable) {
    return this.service.findAll(pageable)
      .map(album -> new AlbumDTO(album, this.generatePhotoDTOList(album)));
  }

  @PostMapping
  public ResponseEntity<AlbumDTO> create(@RequestBody @Valid AlbumDTO albumDTO) throws IOException {
    Album savedAlbum = this.service.save(
      new Album().setName(albumDTO.getName())
    );

    List<ImageDTO> photos = this.generatePhotoDTOList(savedAlbum);

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .pathSegment("{id}")
      .buildAndExpand(savedAlbum.getId())
      .toUri();
    return ResponseEntity.created(uri).body(new AlbumDTO(savedAlbum, photos));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Album album = this.service.findOrFailById(id);
    this.service.delete(album);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("{albumId}/photos")
  public ResponseEntity<ImageDTO> uploadPhoto(
    @PathVariable Long albumId, @RequestParam @Valid @NotNull MultipartFile file
  ) throws IOException {
    Image savedImage = this.service.uploadPhoto(albumId, file);
    return ResponseEntity.ok(
      new ImageDTO(savedImage.getId(), this.generatePhotoURI.apply(albumId, savedImage.getId()))
    );
  }

  @GetMapping("{albumId}/photos/{photoId}")
  public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long albumId, @PathVariable Long photoId) {
    Album album = this.service.findOrFailById(albumId);
    Optional<Image> imageOptional = album.getPhotoById(photoId);

    return imageOptional.map(image -> {
      String fileName = MessageFormat.format("album-{0}-photo-{1}", albumId, photoId);
      return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, image.getMimeType())
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(fileName))
        .body(new ByteArrayResource(image.getContent()));
    }).orElseThrow(() -> {
      throw new EntityNotFoundException("Not found image with id %s for album with id: %s".formatted(photoId,albumId));
    });
  }

  @GetMapping("{id}/photos")
  protected Page<ImageDTO> listPhotos(@PathVariable Long id, @PageableDefault Pageable pageable) {
    Album album = this.service.findOrFailById(id);
    return this.service.findPhotosByAlbum(album, pageable)
      .map(photo -> new ImageDTO(photo.getId(), this.generatePhotoURI.apply(id, photo.getId())));
  }

  private List<ImageDTO> generatePhotoDTOList(Album album) {
    if (Objects.isNull(album.getPhotos())) {
      return null;
    }

    return album.getPhotos().stream()
      .map(photo -> new ImageDTO(photo.getId(), this.generatePhotoURI.apply(album.getId(), photo.getId())))
      .collect(Collectors.toList());
  }

}
