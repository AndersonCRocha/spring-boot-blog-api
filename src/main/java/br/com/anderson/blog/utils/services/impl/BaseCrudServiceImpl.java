package br.com.anderson.blog.utils.services.impl;

import br.com.anderson.blog.utils.services.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public abstract class BaseCrudServiceImpl<ENTITY, ID> implements BaseCrudService<ENTITY, ID> {

  @Override
  public Optional<ENTITY> findById(ID id) {
    return this.getRepository().findById(id);
  }

  @Override
  public ENTITY findOrFailById(ID id) {
    return this.findById(id)
      .orElseThrow(
        () -> new EntityNotFoundException("Entity not found for id: %s".formatted(id))
      );
  }

  @Override
  public List<ENTITY> findAll() {
    return this.getRepository().findAll();
  }

  @Override
  public Page<ENTITY> findAll(Pageable pageable) {
    return this.getRepository().findAll(pageable);
  }

  @Override
  public ENTITY save(ENTITY entity) {
    return this.getRepository().save(entity);
  }

  @Override
  public void delete(ENTITY entity) {
    this.getRepository().delete(entity);
  }

  protected abstract JpaRepository<ENTITY, ID> getRepository();

}
