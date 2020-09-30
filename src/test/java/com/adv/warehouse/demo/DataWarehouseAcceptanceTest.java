package com.adv.warehouse.demo;

import com.adv.warehouse.demo.application.campaign.web.CampaignEventsRestController;
import com.adv.warehouse.demo.application.campaign.web.GroupBy;
import com.adv.warehouse.demo.config.IntegrationSpecification;
import com.adv.warehouse.demo.infrastructure.GlobalRestControllerAdvice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		DemoApplication.class
})
@TestPropertySource(locations = "/application-test.properties")
public class DataWarehouseAcceptanceTest extends IntegrationSpecification {

	@Autowired
	private GlobalRestControllerAdvice globalRestControllerAdvice;

	@Autowired
	private CampaignEventsRestController campaignEventsRestController;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(campaignEventsRestController)
				.setControllerAdvice(globalRestControllerAdvice)
				.setMessageConverters(mappingJackson2HttpMessageConverter)
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}


	@Test
	public void should_find_events_grouped_by_date() throws Exception {
		Integer clicksFrom = 151;
		Integer expectedTotalClicks = 200;
		Integer expectedTotalImpressions = 1000;
		Double expectedClickThroughRate = 20.0;
		String expectedDataSource = "Google Ads";
		String expectedCampaignName = "Adventmarkt Touristik";

		this.mockMvc.perform(get("/api/v1/campaignEvents?dataSourceName={dataSource}&clicksFrom={clicksFrom}", expectedDataSource, clicksFrom))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalClicks").value(expectedTotalClicks))
				.andExpect(jsonPath("$.totalImpressions").value(expectedTotalImpressions))
				.andExpect(jsonPath("$.clickThroughRate").value(expectedClickThroughRate))
				.andExpect(jsonPath("$.campaignsByDates[0].clicks").value(200))
				.andExpect(jsonPath("$.campaignsByDates[0].impressions").value(1000))
				.andExpect(jsonPath("$.campaignsByDates[0].name").value(expectedCampaignName))
				.andExpect(jsonPath("$.campaignsByDates[0].dataSource").value(expectedDataSource))
				.andExpect(jsonPath("$.campaignsByDates", hasSize(1)));
	}

	@Test
	public void should_find_events_grouped_by_data_source() throws Exception {
		Integer expectedTotalClicks = 100;
		Integer expectedTotalImpressions = 1500;
		Double expectedClickThroughRate = 6.67;
		String expectedDataSource = "Twitter Ads";
		String expectedCampaignName = "Adventmarkt Touristik";
		Integer expectedClicks = 100;
		Integer expectedImpressions = 1500;

		Integer impressionsFrom = 1200;
		Integer impressionsTo = 2000;

		this.mockMvc.perform(get("/api/v1/campaignEvents?groupBy={groupBy}&campaignName={campaignName}" +
						"&impressionsFrom={impressionsFrom}&impressionsTo={impressionsTo}",
				GroupBy.DATA_SOURCE, expectedCampaignName, impressionsFrom, impressionsTo))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalClicks").value(expectedTotalClicks))
				.andExpect(jsonPath("$.totalImpressions").value(expectedTotalImpressions))
				.andExpect(jsonPath("$.clickThroughRate").value(expectedClickThroughRate))
				.andExpect(jsonPath("$.campaignsByDataSources[0].dataSource").value(expectedDataSource))
				.andExpect(jsonPath("$.campaignsByDataSources[0].totalClicks").value(expectedClicks))
				.andExpect(jsonPath("$.campaignsByDataSources[0].totalImpressions").value(expectedImpressions))
				.andExpect(jsonPath("$.campaignsByDataSources[0].events", hasSize(1)))
				.andExpect(jsonPath("$.campaignsByDataSources[0].events[0].clicks").value(expectedClicks))
				.andExpect(jsonPath("$.campaignsByDataSources[0].events[0].impressions").value(expectedImpressions))
				.andExpect(jsonPath("$.campaignsByDataSources[0].events[0].name").value(expectedCampaignName))
				.andExpect(jsonPath("$.campaignsByDataSources", hasSize(1)));
	}

	@Test
	public void should_find_events_grouped_by_campaign_name() throws Exception {
		Double expectedClickThroughRate = 6.67;
		String expectedDataSource = "Twitter Ads";
		String expectedCampaignName = "GDN_Retargeting";
		Integer expectedClicks = 120;
		Integer expectedImpressions = 1800;

		Integer impressionsTo = 2000;

		this.mockMvc.perform(get("/api/v1/campaignEvents?groupBy={groupBy}&dataSourceName={dataSource}&campaignName={campaignName}" +
						"&clicksFrom={clicksFrom}&impressionsTo={impressionsTo}",
				GroupBy.CAMPAIGN, expectedDataSource, expectedCampaignName, expectedClicks, impressionsTo))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalClicks").value(expectedClicks))
				.andExpect(jsonPath("$.totalImpressions").value(expectedImpressions))
				.andExpect(jsonPath("$.clickThroughRate").value(expectedClickThroughRate))
				.andExpect(jsonPath("$.campaignsByName[0].name").value(expectedCampaignName))
				.andExpect(jsonPath("$.campaignsByName[0].totalClicks").value(expectedClicks))
				.andExpect(jsonPath("$.campaignsByName[0].totalImpressions").value(expectedImpressions))
				.andExpect(jsonPath("$.campaignsByName[0].events", hasSize(1)))
				.andExpect(jsonPath("$.campaignsByName[0].events[0].clicks").value(expectedClicks))
				.andExpect(jsonPath("$.campaignsByName[0].events[0].impressions").value(expectedImpressions))
				.andExpect(jsonPath("$.campaignsByName[0].events[0].dataSource").value(expectedDataSource))
				.andExpect(jsonPath("$.campaignsByName", hasSize(1)));
	}
}
