package com.uwefuchs.demo.heroestutorial.backend.persistence;

import com.uwefuchs.demo.heroestutorial.backend.model.Hero;

import java.util.Optional;

public interface IHeroesRepository {
    <S extends Hero> S save(S hero);

    Optional<Hero> findById(Integer id);

    Iterable<Hero> findByNameIgnoreCase(String name);

    boolean existsById(Integer id);

    Iterable<Hero> findAll();

    void deleteById(Integer id);

    void delete(Hero hero);
}
