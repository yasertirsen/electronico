package com.example.electronico.service.pattern;

import com.example.electronico.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class CrudTemplate<T> {

    private final JpaRepository<T, Long> repository;

    @Autowired
    protected CrudTemplate(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T add(T object) {
        return repository.save(object);
    }

    public T get(Long objectId) throws NotFoundException {
        return repository.findById(objectId).orElseThrow(NotFoundException::new);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T update(T object) throws NotFoundException {
        return repository.save(object);
    }

    public ResponseEntity<String> delete(Long objectId) throws NotFoundException {
        repository.deleteById(objectId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
}
