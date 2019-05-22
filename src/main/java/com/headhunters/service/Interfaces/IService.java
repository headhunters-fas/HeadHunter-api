package com.headhunters.service.Interfaces;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IService<T> {

    T save(T obj);

    Iterable<T> findAll();

    T findById(Long id);

    void delete(Long id);
}

