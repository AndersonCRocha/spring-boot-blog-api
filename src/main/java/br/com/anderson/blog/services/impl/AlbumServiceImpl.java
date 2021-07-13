package br.com.anderson.blog.services.impl;

import br.com.anderson.blog.models.Album;
import br.com.anderson.blog.models.Image;
import br.com.anderson.blog.repositories.AlbumRepository;
import br.com.anderson.blog.security.utils.AuthenticationUtils;
import br.com.anderson.blog.services.AlbumService;
import br.com.anderson.blog.utils.BasePersistent;
import br.com.anderson.blog.utils.PersistentUtils;
import br.com.anderson.blog.utils.services.impl.BaseCrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;

@Service
public class AlbumServiceImpl extends BaseCrudServiceImpl<Album, Long> implements AlbumService {

  private final AlbumRepository repository;
  private final AuthenticationUtils authenticationUtils;

  public AlbumServiceImpl(AlbumRepository repository, AuthenticationUtils authenticationUtils) {
    this.repository = repository;
    this.authenticationUtils = authenticationUtils;
  }

  @Override
  protected AlbumRepository getRepository() {
    return this.repository;
  }

  @Transactional
  @Override
  public Album save(Album album) {
    album.setOwner(this.authenticationUtils.getAuthenticatedUser());
    return super.save(album);
  }

  @Override
  public void delete(Album album) {
    if (PersistentUtils.notEquals(album.getOwner(), this.authenticationUtils.getAuthenticatedUser())) {
      throw new AccessDeniedException("The album can only be deleted by its owner");
    }
    super.delete(album);
  }

  @Override
  public Page<Image> findPhotosByAlbum(Album album, Pageable pageable) {
    return this.repository.findPhotosByAlbum(album, pageable);
  }

  @Override
  public Image uploadPhoto(Long albumId, MultipartFile file) throws IOException {
    Album album = this.findOrFailById(albumId);
    Image image = new Image()
      .setContent(file.getBytes())
      .setMimeType(file.getContentType())
      .setAlbum(album);

    album.addPhoto(image);

    return this.save(album).getPhotos().stream()
      .max(Comparator.comparing(BasePersistent::getCreatedAt))
      .orElse(image);
  }

}
