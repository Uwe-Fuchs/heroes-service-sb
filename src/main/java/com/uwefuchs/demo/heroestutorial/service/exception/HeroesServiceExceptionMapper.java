package com.uwefuchs.demo.heroestutorial.service.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class HeroesServiceExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger LOG = LoggerFactory.getLogger(HeroesServiceExceptionMapper.class);

	@Override
	public Response toResponse(Throwable e) {		
		LOG.error("Fehler beim Ausführen des Heroes-Service!", e);
		
		if (WebApplicationException.class.isAssignableFrom(e.getClass())) {
			WebApplicationException wex = (WebApplicationException) e;			
			return wex.getResponse();
		}
        
        return Response
        		.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "interner Server-Fehler!"))
                .build();
	}
}
