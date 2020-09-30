package com.adv.warehouse.demo.application.campaign.domain;

import com.adv.warehouse.demo.domain.campaign.CampaignDayEvent;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CampaignDayEventCustomRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	public SearchHits<CampaignDayEvent> search(String dataSource, String campaignName,
	                                           LocalDateTime dateFrom, LocalDateTime dateTo,
	                                           Integer impressionsFrom, Integer impressionsTo,
	                                           Integer clicksFrom, Integer clicksTo) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000000);

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

		if (!StringUtils.isEmpty(dataSource)) {
			queryBuilder.must(new WildcardQueryBuilder("dataSource.keyword", dataSource));
		}

		if (!StringUtils.isEmpty(campaignName)) {
			queryBuilder.must(new WildcardQueryBuilder("name.keyword", campaignName));
		}

		boolean dateFromNotNull = Objects.nonNull(dateFrom);
		boolean dateToNotNull = Objects.nonNull(dateTo);

		if (dateFromNotNull || dateToNotNull) {
			RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("date");

			if (dateFromNotNull) {
				rangeQueryBuilder.from(dateFrom.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
			}

			if (dateToNotNull) {
				rangeQueryBuilder.to(dateTo.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
			}
			queryBuilder.must(rangeQueryBuilder);
		}


		if (Objects.nonNull(impressionsFrom)) {
			queryBuilder.must(new RangeQueryBuilder("impressions").gte(impressionsFrom));
		}

		if (Objects.nonNull(impressionsTo)) {
			queryBuilder.must(new RangeQueryBuilder("impressions").lte(impressionsTo));
		}


		if (Objects.nonNull(clicksFrom)) {
			queryBuilder.must(new RangeQueryBuilder("clicks").gte(clicksFrom));
		}

		if (Objects.nonNull(clicksTo)) {
			queryBuilder.must(new RangeQueryBuilder("clicks").lte(clicksTo));
		}

		Query query = new NativeSearchQueryBuilder().withQuery(searchSourceBuilder.query(queryBuilder).query())
				.addAggregation(AggregationBuilders.sum("sum_impressions").field("impressions"))
				.addAggregation(AggregationBuilders.sum("sum_clicks").field("clicks"))
				.build();

		return elasticsearchOperations.search(query, CampaignDayEvent.class);
	}

}
