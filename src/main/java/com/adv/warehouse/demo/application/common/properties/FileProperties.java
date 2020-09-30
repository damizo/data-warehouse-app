package com.adv.warehouse.demo.application.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "data.warehouse.file")
public class FileProperties {
	private String name;
}
