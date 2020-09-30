package com.adv.warehouse.demo.application.campaign.web;

import com.adv.warehouse.demo.application.campaign.domain.CampaignDayEventApplicationService;
import com.adv.warehouse.demo.application.campaign.web.dto.CampaignFullDataDTO;
import com.adv.warehouse.demo.application.campaign.web.dto.CampaignSearchParamsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/campaignEvents")
@RequiredArgsConstructor
public class CampaignEventsRestController {

	private final CampaignDayEventApplicationService campaignEventApplicationService;

	@GetMapping
	public CampaignFullDataDTO search(@RequestParam(required = false) String dataSourceName,
	                                  @RequestParam(required = false) String campaignName,
	                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
	                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
	                                  @RequestParam(required = false) Integer impressionsFrom,
	                                  @RequestParam(required = false) Integer impressionsTo,
	                                  @RequestParam(required = false) Integer clicksFrom,
	                                  @RequestParam(required = false) Integer clicksTo,
	                                  @RequestParam(required = false, defaultValue = "DATE") GroupBy groupBy
	) {
		return campaignEventApplicationService.search(CampaignSearchParamsDTO.builder()
				.dataSourceName(dataSourceName)
				.campaignName(campaignName)
				.from(from)
				.to(to)
				.clicksFrom(clicksFrom)
				.clicksTo(clicksTo)
				.impressionsFrom(impressionsFrom)
				.impressionsTo(impressionsTo)
				.groupBy(groupBy)
				.build());
	}


}
