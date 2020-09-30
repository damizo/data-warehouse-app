package com.adv.warehouse.demo.application.campaign.domain;

import com.adv.warehouse.demo.domain.campaign.CampaignDayEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CampaignDayEventRepository extends ElasticsearchRepository<CampaignDayEvent, Long> {

}
