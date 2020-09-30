package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRecordDTO extends MetricsDTO {
	private String name;
	private String dataSource;
	private LocalDateTime date;
	private Integer clicks;
	private Integer impressions;

	@Builder
	public CampaignRecordDTO(LocalDateTime date, String dataSource, String name, Integer impressions, Integer clicks) {
		this.date = date;
		this.dataSource = dataSource;
		this.name = name;
		this.impressions = impressions;
		this.clicks = clicks;
	}

}
