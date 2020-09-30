package com.adv.warehouse.demo.application.warehouse.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignCSVModel {

	private String dataSource;
	private String name;
	private String date;
	private Integer clicks;
	private Integer impressions;

	@RequiredArgsConstructor
	public enum ColumnMappings {
		DATA_SOURCE(0),
		NAME(1),
		DATE(2),
		CLICKS(3),
		IMPRESSIONS(4);

		@Getter
		private final Integer index;
	}

}
