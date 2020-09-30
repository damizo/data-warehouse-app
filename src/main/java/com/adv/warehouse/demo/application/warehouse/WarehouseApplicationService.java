package com.adv.warehouse.demo.application.warehouse;

import com.adv.warehouse.demo.application.campaign.domain.CampaignDayEventDomainService;
import com.adv.warehouse.demo.application.common.properties.FileProperties;
import com.adv.warehouse.demo.domain.campaign.CampaignDayEvent;
import com.adv.warehouse.demo.infrastructure.Profiles;
import com.adv.warehouse.demo.infrastructure.annotations.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ApplicationService
@RequiredArgsConstructor
@Profile(Profiles.LOADER)
@Slf4j
public class WarehouseApplicationService {

	private final CsvHandler csvHandler;
	private final CampaignDayEventDomainService campaignDomainService;
	private final FileProperties fileProperties;
	private static final String DATE_FORMAT = "MM/dd/yy";

	@PostConstruct
	public void init() {
		campaignDomainService.clearIndex();
		csvHandler.extract(fileProperties.getName())
				.stream()
				.skip(1)
				.map(csvHandler::transform)
				.forEach(campaignDay -> {
					try {
						log.info("Saving event: {}", campaignDay);
						campaignDomainService.createOrUpdate(CampaignDayEvent.builder()
								.id(UUID.randomUUID().toString())
								.name(campaignDay.getName())
								.date(LocalDateTime.of(LocalDate
										.parse(campaignDay.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT)), LocalTime.MIDNIGHT)
										.toInstant(ZoneOffset.ofTotalSeconds(0))
										.toEpochMilli()
								)
								.clicks(campaignDay.getClicks())
								.impressions(campaignDay.getImpressions())
								.dataSource(campaignDay.getDataSource())
								.build());
					} catch (Exception e) {
						log.error(e.toString(), e);
					}
				});
	}
}
