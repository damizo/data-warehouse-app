package com.adv.warehouse.demo.application.campaign.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignFullDataDTO {
	private Double clickThroughRate;
	private Integer totalClicks;
	private Integer totalImpressions;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CampaignByNameDTO> campaignsByName = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CampaignByDataSourceDTO> campaignsByDataSources = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CampaignRecordDTO> campaignsByDates = new ArrayList<>();
}
