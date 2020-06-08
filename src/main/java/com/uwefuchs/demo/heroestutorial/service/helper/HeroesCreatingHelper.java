package com.uwefuchs.demo.heroestutorial.service.helper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uwefuchs.demo.heroestutorial.service.model.Hero;

public class HeroesCreatingHelper {
	private static volatile int heroId = 10;
	private static final Logger LOG = LoggerFactory.getLogger(HeroesCreatingHelper.class);

	public static Map<Integer, Hero> buildHeroesMap() {
	    Map<Integer, Hero> heroesMap = new LinkedHashMap<>();
	    
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
