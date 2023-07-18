package com.uwefuchs.demo.heroestutorial.backend.persistence;

import com.uwefuchs.demo.heroestutorial.backend.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHeroesRepository extends JpaRepository<Hero, Integer>, IHeroesRepository {
}
