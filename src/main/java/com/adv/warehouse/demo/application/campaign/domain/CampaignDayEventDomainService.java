package com.adv.warehouse.demo.application.campaign.domain;

import com.adv.warehouse.demo.application.common.domain.AbstractDomainService;
import com.adv.warehouse.demo.domain.campaign.CampaignDayEvent;
import com.adv.warehouse.demo.infrastructure.annotations.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.time.LocalDateTime;

@DomainService
@Slf4j
public class CampaignDayEventDomainService extends AbstractDomainService<CampaignDayEvent, Long, CampaignDayEventRepository> {

	private final CampaignDayEventCustomRepository campaignDayEventCustomRepository;

	public CampaignDayEventDomainService(CampaignDayEventCustomRepository campaignDayEventCustomRepository, CampaignDayEventRepository campaignDayEventRepository) {
		super(campaignDayEventRepository);
		this.campaignDayEventCustomRepository = campaignDayEventCustomRepository;
	}

	public SearchHits<CampaignDayEvent> getCampaigns(String dataSource, String campaignName,
	                                                 LocalDateTime dateFrom, LocalDateTime dateTo,
	                                                 Integer impressionsFrom, Integer impressionsTo,
	                                                 Integer clicksFrom, Integer clicksTo) {
		return campaignDayEventCustomRepository.search(dataSource, campaignName, dateFrom, dateTo, impressionsFrom, impressionsTo, clicksFrom, clicksTo);
	}

	public void clearIndex() {
		this.r.deleteAll();
	}

}
