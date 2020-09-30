package com.adv.warehouse.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
public class IntegrationSpecification {

	@Autowired
	protected MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

	protected MockMvc mockMvc;

	@Configuration
	static class BaseConfiguration {

		@Bean
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return new MappingJackson2HttpMessageConverter(mapper);
		}
	}
}
