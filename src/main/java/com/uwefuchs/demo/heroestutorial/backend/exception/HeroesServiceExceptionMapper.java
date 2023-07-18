package com.uwefuchs.demo.heroestutorial.backend.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
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
		LOG.error("Error in heroes-service-backend!", e);

		if (HeroNotFoundException.class.isAssignableFrom(e.getClass())) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
					.build();
		}

		if (HeroesException.class.isAssignableFrom(e.getClass())
				|| IllegalArgumentException.class.isAssignableFrom(e.getClass())) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage()))
					.build();
		}

		if (WebApplicationException.class.isAssignableFrom(e.getClass())) {
			WebApplicationException wex = (WebApplicationException) e;
			return Response
					.status(wex.getResponse().getStatusInfo())
					.entity(new ErrorMessage(wex.getResponse().getStatus(), wex.getMessage()))
					.build();
		}
        
        return Response
        		.status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
	}
}
