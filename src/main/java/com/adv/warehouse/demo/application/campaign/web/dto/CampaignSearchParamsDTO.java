package com.adv.warehouse.demo.application.campaign.web.dto;


import com.adv.warehouse.demo.application.campaign.web.GroupBy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignSearchParamsDTO {
	private String dataSourceName;
	private String campaignName;
	private LocalDateTime from;
	private LocalDateTime to;
	private Integer impressionsFrom;
	private Integer impressionsTo;
	private Integer clicksFrom;
	private Integer clicksTo;
	private GroupBy groupBy;

}
