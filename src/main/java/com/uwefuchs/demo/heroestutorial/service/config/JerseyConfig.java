package com.uwefuchs.demo.heroestutorial.service.config;

import java.util.Collections;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.uwefuchs.demo.heroestutorial.service.exception.HeroesServiceExceptionMapper;
import com.uwefuchs.demo.heroestutorial.service.helper.CORSResponseFilter;
import com.uwefuchs.demo.heroestutorial.service.resource.HelloResource;
import com.uwefuchs.demo.heroestutorial.service.resource.HeroesResource;

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