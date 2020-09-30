package com.adv.warehouse.demo.domain.campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "campaign_events_idx", type = "campaign_events", shards = 3, replicas = 3)
public class CampaignDayEvent {

	@Id
	private String id;
	private String name;
	private String dataSource;
	private Long date;
	private Integer clicks;
	private Integer impressions;

}
