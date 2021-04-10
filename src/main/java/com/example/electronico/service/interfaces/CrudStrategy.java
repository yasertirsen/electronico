package com.example.electronico.service.interfaces;

import com.example.electronico.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudStrategy<T> {
    T add(T object);

    T get(Long objectId) throws NotFoundException;

    List<T> getAll();

    T update(T object) throws NotFoundException;

    ResponseEntity<String> delete(Long objectId) throws NotFoundException;
}
