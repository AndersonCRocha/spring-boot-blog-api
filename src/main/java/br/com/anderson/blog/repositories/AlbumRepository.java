package br.com.anderson.blog.repositories;

import br.com.anderson.blog.models.Album;
import br.com.anderson.blog.models.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Long> {

  @Query("SELECT i FROM Image i WHERE i.album IS NOT NULL AND i.album = :album")
  Page<Image> findPhotosByAlbum(Album album, Pageable pageable);

}
