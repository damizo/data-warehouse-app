package com.adv.warehouse.demo.application.warehouse;

import com.adv.warehouse.demo.application.common.exception.ErrorType;
import com.adv.warehouse.demo.application.common.exception.ParameterizedException;
import com.adv.warehouse.demo.application.warehouse.domain.CampaignCSVModel;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

/**
 * native solution (reading each line separately) is more efficient for bigger files
 */

@Component
@Slf4j
public class CsvHandler {

	public List<String> extract(String fileName) {
		try {
			URL resource = Optional.ofNullable(ClassLoader.getSystemResource(fileName))
					.orElseThrow(() -> new ParameterizedException(ErrorType.FILE_NOT_FOUND));
			return Files.readLines(new File(resource.toURI()), Charset.defaultCharset());
		} catch (Exception e) {
			log.error(e.toString());
			throw new ParameterizedException(ErrorType.EXTRACTION_ERROR);
		}
	}

	public CampaignCSVModel transform(String record) {
		String[] split = record.split(",");

		String name = split[CampaignCSVModel.ColumnMappings.NAME.getIndex()];
		String dataSource = split[CampaignCSVModel.ColumnMappings.DATA_SOURCE.getIndex()];
		String date = split[CampaignCSVModel.ColumnMappings.DATE.getIndex()];
		Integer impressions = Integer.valueOf(split[CampaignCSVModel.ColumnMappings.IMPRESSIONS.getIndex()]);
		Integer clicks = Integer.valueOf(split[CampaignCSVModel.ColumnMappings.CLICKS.getIndex()]);

		return CampaignCSVModel.builder()
				.name(name)
				.dataSource(dataSource)
				.date(date)
				.impressions(impressions)
				.clicks(clicks)
				.build();
	}

}
