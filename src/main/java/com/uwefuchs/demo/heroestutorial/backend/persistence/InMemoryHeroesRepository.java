package com.uwefuchs.demo.heroestutorial.backend.persistence;

import com.uwefuchs.demo.heroestutorial.backend.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class InMemoryHeroesRepository implements IHeroesRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryHeroesRepository.class);

    private final Map<Integer, Hero> heroesMap;

    public InMemoryHeroesRepository() {
        super();
        this.heroesMap = HeroesCreatingHelper.buildHeroesMap();
    }

    @Override
    public <S extends Hero> S save(S hero) {
        Assert.notNull(hero, "hero must not be null");
        Assert.hasText(hero.getName(), "a hero always has a name!");

        LOG.debug("creating hero with name [{}]...", hero.getName());
        if (hero.getId() == 0) {
            final Hero newHero = new Hero(HeroesCreatingHelper.getNextId(), hero.getName());
            heroesMap.put(newHero.getId(), newHero);
            LOG.debug("hero [{}] successfully created with id [{}].", newHero.getName(), newHero.getId());
            return (S) newHero;
        } else {
            heroesMap.put(hero.getId(), hero);
            LOG.debug("hero [{}] with id [{}] successfully updated.", hero.getName(), hero.getId());
            return (S) hero;
        }
    }

    @Override
    public Optional<Hero> findById(Integer id) {
        LOG.debug("delivering hero with id [{}]...", id);
        final Hero hero = heroesMap.get(id);

        if (hero == null) {
            return Optional.empty();
        } else {
            return Optional.of(hero);
        }
    }

    @Override
    public Iterable<Hero> findByNameIgnoreCase(String name) {
        final Collection<Hero> allHeroes = new ArrayList<>();

        LOG.debug("searching heroes by name [{}]...", name);
        this.findAll().forEach(h -> {
            if (name.equalsIgnoreCase(h.getName())) {
                allHeroes.add(h);
            }});

        return allHeroes;
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<Hero> findAll() {
        LOG.debug("delivering all heroes...");
        return heroesMap.values();
    }

    @Override
    public void deleteById(Integer id) {
        LOG.debug("deleting hero with id [{}]...", id);
        heroesMap.remove(id);
        LOG.debug("hero with id [{}] deleted.", id);
    }

    @Override
    public void delete(Hero hero) {
        deleteById(hero.getId());
    }

    public static class HeroesCreatingHelper {
        private static int heroId = 10;
        private static final Logger LOG = LoggerFactory.getLogger(HeroesCreatingHelper.class);

        public static LinkedHashMap<Integer, Hero> buildHeroesMap() {
            LinkedHashMap<Integer, Hero> heroesMap = new LinkedHashMap<>();

            LOG.debug("building heroes-map...");

            int id = getNextId();
            heroesMap.put(id, new Hero(id, "Mr. Nice"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Narco"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Bombasto"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Celeritas"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Magneta"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "RubberMan"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Dynama"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Dr IQ"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Magma"));

            id = getNextId();
            heroesMap.put(id, new Hero(id, "Tornado"));

            LOG.debug("... finished!");

            return heroesMap;
        }

        public static int getNextId() {
            return ++heroId;
        }
    }
}
