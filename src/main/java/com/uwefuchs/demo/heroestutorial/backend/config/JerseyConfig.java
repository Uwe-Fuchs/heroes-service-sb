package com.uwefuchs.demo.heroestutorial.backend.config;

import java.util.Collections;

import com.uwefuchs.demo.heroestutorial.backend.exception.HeroesServiceExceptionMapper;
import com.uwefuchs.demo.heroestutorial.backend.helper.CORSResponseFilter;
import com.uwefuchs.demo.heroestutorial.backend.resource.HelloResource;
import com.uwefuchs.demo.heroestutorial.backend.resource.HeroesResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(HelloResource.class);
		register(HeroesResource.class);
		register(HeroesServiceExceptionMapper.class);
		register(CORSResponseFilter.class);
		setProperties(Collections.singletonMap(
				"jersey.config.server.response.setStatusOverSendError", true));
	}
}