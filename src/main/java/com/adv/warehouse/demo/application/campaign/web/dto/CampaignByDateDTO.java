package com.adv.warehouse.demo.application.campaign.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignByDateDTO {

	LocalDateTime date;
	List<CampaignByDateRecordDTO> events = new ArrayList<>();

}
