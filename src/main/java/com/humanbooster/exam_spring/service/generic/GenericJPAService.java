package com.humanbooster.exam_spring.service.generic;

import com.humanbooster.exam_spring.utils.ModelUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public abstract class GenericJPAService<T, ID> implements GenericService<T, ID> {

    private final JpaRepository<T, ID> repository;


    public T create(T entity) {
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public Optional<T> deleteById(ID id) {
        Optional<T> entity = repository.findById(id);
        entity.ifPresent(repository::delete);
        return entity;
    }

    public Optional<T> update(T newEntity, ID id) {
        return repository.findById(id)
                .map(existingEntity -> {
                    ModelUtil.copyFields(newEntity, existingEntity);
                    return repository.save(existingEntity);
                });
    }
}