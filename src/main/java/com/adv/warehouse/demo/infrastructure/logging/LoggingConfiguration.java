package com.adv.warehouse.demo.infrastructure.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.JsonHttpLogFormatter;
import org.zalando.logbook.Logbook;

@Configuration
public class LoggingConfiguration {

	@Bean
	public Logbook logbook() {
		return Logbook.builder()
				.formatter(new JsonHttpLogFormatter(objectMapper()))
				.build();
	}

	ObjectMapper objectMapper() {
		return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}
}
