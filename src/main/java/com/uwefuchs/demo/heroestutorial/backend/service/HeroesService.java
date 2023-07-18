package com.uwefuchs.demo.heroestutorial.backend.service;

import com.uwefuchs.demo.heroestutorial.backend.exception.HeroNotFoundException;
import com.uwefuchs.demo.heroestutorial.backend.model.Hero;
import com.uwefuchs.demo.heroestutorial.backend.persistence.IHeroesRepository;
import org.glassfish.jersey.internal.guava.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class HeroesService {
    private static final Logger LOG = LoggerFactory.getLogger(HeroesService.class);

    @Inject
    //@Qualifier("inMemoryHeroesRepository")
    @Qualifier("jpaHeroesRepository")
    private IHeroesRepository heroesRepository;

    public Collection<Hero> retrieveAllHeroes() {
        final Collection<Hero> allHeroes = new ArrayList<>();
        LOG.info("delivering all heroes...");
        heroesRepository.findAll().forEach(allHeroes::add);
        return allHeroes;
    }

    public Hero retrieveHeroById(Integer id) {
        Assert.notNull(id, "hero-id must not be null");
        if (!heroesRepository.existsById(id)) {
            throw new HeroNotFoundException(String.format("no hero found with id [%d]", id));
        }

        LOG.info("delivering hero with id [{}]...", id);
        return heroesRepository.findById(id).orElse(null);
    }

    public Collection<Hero> retrieveHeroesByName(String heroName) {
        Assert.notNull(heroName, "heroName must not be null");
        final Collection<Hero> allHeroes = new ArrayList<>();

        LOG.info("searching heroes by name [{}]...", heroName);
        return Lists.newArrayList(heroesRepository.findByNameIgnoreCase(heroName));
    }

    public Hero updateHero(Hero hero, Integer id) {
        Assert.notNull(hero, "hero must not be null");
        Assert.notNull(id, "hero-id must not be null");
        Assert.hasText(hero.getName(), "a hero always has a name!");

        if (heroesRepository.existsById(id)) {
            throw new HeroNotFoundException(String.format("no hero found with id [%d]", hero.getId()));
        }

        LOG.info("updating hero with id [{}]...", hero.getId());
        return heroesRepository.save(hero);
    }

    public void deleteHeroById(Integer id) {
        Assert.notNull(id, "hero-id must not be null");
        if (heroesRepository.existsById(id)) {
            throw new HeroNotFoundException(String.format("no hero found with id [%d]", id));
        }

        heroesRepository.deleteById(id);
        LOG.info("hero with id [{}] deleted.", id);
    }

    public Hero createHero(Hero hero) {
        Assert.notNull(hero, "hero must not be null");
        Assert.hasText(hero.getName(), "a hero always has a name!");
        Hero newHero = heroesRepository.save(hero);
        LOG.info("hero [{}] successfully created with id [{}].", newHero.getName(), newHero.getId());

        return newHero;
    }
}
