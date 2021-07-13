package br.com.anderson.blog.services;

import br.com.anderson.blog.models.Album;
import br.com.anderson.blog.models.Image;
import br.com.anderson.blog.utils.services.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AlbumService extends BaseCrudService<Album, Long> {

  Page<Image> findPhotosByAlbum(Album album, Pageable pageable);
  Image uploadPhoto(Long albumId, MultipartFile file) throws IOException;

}
