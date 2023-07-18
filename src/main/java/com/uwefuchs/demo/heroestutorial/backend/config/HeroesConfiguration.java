package com.uwefuchs.demo.heroestutorial.backend.config;

import com.uwefuchs.demo.heroestutorial.backend.persistence.IHeroesRepository;
import com.uwefuchs.demo.heroestutorial.backend.persistence.InMemoryHeroesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroesConfiguration {
//    @Bean(name = "heroesRepository")
//    public IHeroesRepository inMemoryHeroesRepository() {
//        return new InMemoryHeroesRepository();
//    }
}
