package com.adv.warehouse.demo.application.campaign.domain;


import com.adv.warehouse.demo.application.campaign.web.dto.*;
import com.adv.warehouse.demo.domain.campaign.CampaignDayEvent;
import com.adv.warehouse.demo.infrastructure.annotations.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static com.adv.warehouse.demo.application.common.utils.CustomMath.round;

@ApplicationService
@RequiredArgsConstructor
@Slf4j
public class CampaignDayEventApplicationService {

	private final CampaignDayEventDomainService campaignEventDomainService;

	public CampaignFullDataDTO search(CampaignSearchParamsDTO params) {
		SearchHits<CampaignDayEvent> campaigns = getCampaigns(params);

		if (!campaigns.hasSearchHits()){
			return CampaignFullDataDTO.builder()
					.build();
		}

		CampaignFullDataDTO.CampaignFullDataDTOBuilder builder = buildAggregatedData(campaigns);


		switch (params.getGroupBy()) {
			case CAMPAIGN:
				return builder
						.campaignsByName(extractGroupingByCampaignName(campaigns))
						.build();
			case DATA_SOURCE:
				return builder
						.campaignsByDataSources(extractGroupingByDataSource(campaigns))
						.build();
			default:
			case DATE:
				return builder
						.campaignsByDates(extractGroupingByDate(campaigns))
						.build();

		}
	}

	private List<CampaignRecordDTO> extractGroupingByDate(SearchHits<CampaignDayEvent> campaigns) {
		return campaigns.get().map(SearchHit::getContent)
				.map(record -> CampaignRecordDTO.builder()
						.date(new Timestamp(record.getDate()).toLocalDateTime())
						.clicks(record.getClicks())
						.dataSource(record.getDataSource())
						.impressions(record.getImpressions())
						.name(record.getName())
						.build()).collect(Collectors.toList());
	}

	private List<CampaignByNameDTO> extractGroupingByCampaignName(SearchHits<CampaignDayEvent> campaigns) {
		return campaigns.get().map(SearchHit::getContent)
				.collect(Collectors.groupingBy(CampaignDayEvent::getName))
				.entrySet()
				.stream()
				.map((entry) -> CampaignByNameDTO.builder()
						.name(entry.getKey())
						.events(entry.getValue().stream().map(campaignDayEvent -> CampaignByNameRecordDTO.builder()
								.dataSource(campaignDayEvent.getDataSource())
								.date(new Timestamp(campaignDayEvent.getDate()).toLocalDateTime())
								.clicks(campaignDayEvent.getClicks())
								.impressions(campaignDayEvent.getImpressions())
								.build())
								.collect(Collectors.toList()))
						.build()).collect(Collectors.toList());
	}

	private List<CampaignByDataSourceDTO> extractGroupingByDataSource(SearchHits<CampaignDayEvent> campaigns) {
		return campaigns.get().map(SearchHit::getContent)
				.collect(Collectors.groupingBy(CampaignDayEvent::getDataSource))
				.entrySet()
				.stream()
				.map((entry) -> CampaignByDataSourceDTO.builder()
						.dataSource(entry.getKey())
						.events(entry.getValue().stream().map(campaignDayEvent -> CampaignByDataSourceRecordDTO.builder()
								.date(new Timestamp(campaignDayEvent.getDate()).toLocalDateTime())
								.clicks(campaignDayEvent.getClicks())
								.name(campaignDayEvent.getName())
								.impressions(campaignDayEvent.getImpressions())
								.build())
								.collect(Collectors.toList()))
						.build()).collect(Collectors.toList());
	}


	private CampaignFullDataDTO.CampaignFullDataDTOBuilder buildAggregatedData(SearchHits<CampaignDayEvent> campaigns) {
		Aggregations aggregations = campaigns.getAggregations();
		ParsedSum sumClicks = aggregations.get("sum_clicks");
		ParsedSum sumImpressions = aggregations.get("sum_impressions");
		int totalClicks = Double.valueOf(sumClicks.getValue()).intValue();
		int totalImpressions = Double.valueOf(sumImpressions.getValue()).intValue();
		double clickThroughRate = round(Double.valueOf(totalClicks) / Double.valueOf(totalImpressions) * 100);

		return CampaignFullDataDTO.builder()
				.totalClicks(totalClicks)
				.totalImpressions(totalImpressions)
				.clickThroughRate(clickThroughRate);
	}

	private SearchHits<CampaignDayEvent> getCampaigns(CampaignSearchParamsDTO params) {
		return campaignEventDomainService
				.getCampaigns(params.getDataSourceName(),
						params.getCampaignName(),
						params.getFrom(),
						params.getTo(),
						params.getImpressionsFrom(),
						params.getImpressionsTo(),
						params.getClicksFrom(),
						params.getClicksTo());
	}

}
