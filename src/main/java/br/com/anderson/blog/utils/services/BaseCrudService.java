package br.com.anderson.blog.utils.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<ENTITY, ID> {

  Optional<ENTITY> findById(ID id);
  ENTITY findOrFailById(ID id);

  List<ENTITY> findAll();
  Page<ENTITY> findAll(Pageable pageable);

  ENTITY save(ENTITY entity);

  void delete(ENTITY entity);

}
