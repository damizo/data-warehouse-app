package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignByNameRecordDTO  extends MetricsDTO{
	private String dataSource;
	private LocalDateTime date;

	@Builder
	public CampaignByNameRecordDTO(String dataSource, LocalDateTime date, Integer impressions, Integer clicks){
		this.dataSource = dataSource;
		this.date = date;
		this.impressions = impressions;
		this.clicks = clicks;
	}
}
