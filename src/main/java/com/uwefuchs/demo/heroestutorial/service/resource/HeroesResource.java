package com.uwefuchs.demo.heroestutorial.service.resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uwefuchs.demo.heroestutorial.service.helper.HeroesCreatingHelper;
import com.uwefuchs.demo.heroestutorial.service.model.Hero;

@Path("heroes")
public class HeroesResource {
    private static final Logger LOG = LoggerFactory.getLogger(HeroesResource.class);
    private static final Map<Integer, Hero> HEROES_MAP = HeroesCreatingHelper.buildHeroesMap();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Hero> getAllHeroes() {
        LOG.debug("delivering all heroes...");
        return HEROES_MAP.values();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Hero findHero(@PathParam("id") Integer id) {
    	if (!HEROES_MAP.containsKey(id)) {
    		throw new WebApplicationException(String.format("no hero found with id [%d]", id), 404);
    	}
    	
        LOG.debug("delivering hero with id [{}]...", id);
        return HEROES_MAP.get(id);
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Hero> searchHeroesByName(@QueryParam("name") String name) {
        LOG.debug("searching heroes by name [{}]...", name);

        Set<Hero> result = new HashSet<>();
        result.addAll(HEROES_MAP.values());        

        return result
            .stream()
            .filter(h -> h.nameContains(name))
            .collect(Collectors.toList());
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHero(Hero hero, @PathParam("id") Integer id) {
    	if (!HEROES_MAP.containsKey(id)) {
    		throw new WebApplicationException(String.format("no hero found with id [%d]", id), 404);
    	}

    	if (hero.getName() == null || "".equals(hero.getName())) {
    		throw new WebApplicationException("heroes must have a name!", 400);
    	}
    	
        LOG.debug("updating hero with id [{}]...", id);        
        HEROES_MAP.put(id, hero);
        LOG.info("hero with id [{}] updated.", id);
        
        return Response.ok().build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHero(@Context UriInfo uriInfo, Hero hero) {
    	if (hero.getName() == null || "".equals(hero.getName())) {
    		throw new WebApplicationException("heroes must have a name!", 400);
    	}
    	
    	Hero newHero = new Hero(HeroesCreatingHelper.getNextId(), hero.getName());
    	
        LOG.debug("creating hero with id [{}] and name [{}]...", newHero.getId(), newHero.getName());        
        HEROES_MAP.put(newHero.getId(), newHero);
        LOG.info("hero [{}] successfully created.", newHero.getName());
        
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder()
				.path("heroes")
				.path(Integer.toString(newHero.getId()));
        
        return Response
            .created(uriBuilder.build())
            .entity(newHero)
            .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteHero(@PathParam("id") Integer id) {
    	if (!HEROES_MAP.containsKey(id)) {
    		throw new WebApplicationException(String.format("no hero found with id [%d]", id), 404);
    	}
    	
        LOG.debug("deleting hero with id [{}]...", id);        
        HEROES_MAP.remove(id);
        LOG.info("hero with id [{}] deleted.", id);
        
        return Response.ok().build();
    }
}
