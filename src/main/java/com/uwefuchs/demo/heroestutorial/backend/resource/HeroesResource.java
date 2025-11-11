package com.uwefuchs.demo.heroestutorial.backend.resource;

import com.uwefuchs.demo.heroestutorial.backend.model.Hero;
import com.uwefuchs.demo.heroestutorial.backend.service.HeroesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("heroes")
public class HeroesResource {
    private static final Logger LOG = LoggerFactory.getLogger(HeroesResource.class);

    @Inject
    private HeroesService heroesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHeroes() {
        return Response
                .ok()
                .entity(heroesService.retrieveAllHeroes())
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findHero(@PathParam("id") Integer id) {
        return Response
                .ok()
                .entity(heroesService.retrieveHeroById(id))
                .build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchHeroesByName(@QueryParam("name") String name) {
        return Response
                .ok()
                .entity(heroesService.retrieveHeroesByName(name))
                .build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHero(Hero hero, @PathParam("id") Integer id) {
        return Response
                .ok()
                .entity(heroesService.updateHero(hero, id))
                .build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHero(@Context UriInfo uriInfo, Hero hero) {
    	final Hero newHero = heroesService.createHero(hero);
		final UriBuilder uriBuilder = uriInfo.getBaseUriBuilder()
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
        heroesService.deleteHeroById(id);
        return Response
                .ok()
                .build();
    }
}
