package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.adv.warehouse.demo.application.common.utils.CustomMath.round;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignByDataSourceDTO {
	private String dataSource;
	private List<CampaignByDataSourceRecordDTO> events = new ArrayList<>();

	public Integer getTotalClicks(){
		return this.events.stream().map(CampaignByDataSourceRecordDTO::getClicks)
				.reduce(Integer::sum)
				.orElse(0);
	}

	public Integer getTotalImpressions(){
		return this.events.stream().map(CampaignByDataSourceRecordDTO::getImpressions)
				.reduce(Integer::sum)
				.orElse(0);
	}

	public Double getClickThroughRate(){
		return round(Double.valueOf(getTotalClicks()) / Double.valueOf(getTotalImpressions()) * 100);
	}
}
