package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignByDateRecordDTO extends MetricsDTO {
	private String name;
	private String dataSource;

	@Builder
	public CampaignByDateRecordDTO(String dataSource, String name, Integer impressions, Integer clicks) {
		this.dataSource = dataSource;
		this.name = name;
		this.impressions = impressions;
		this.clicks = clicks;
	}
}
