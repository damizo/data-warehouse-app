package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignByDataSourceRecordDTO extends MetricsDTO {
	private String name;
	private LocalDateTime date;

	@Builder
	public CampaignByDataSourceRecordDTO(String name, LocalDateTime date, Integer impressions, Integer clicks) {
		this.name = name;
		this.date = date;
		this.impressions = impressions;
		this.clicks = clicks;
	}
}
