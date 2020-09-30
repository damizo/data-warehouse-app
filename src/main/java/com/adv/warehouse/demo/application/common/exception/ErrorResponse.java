package com.adv.warehouse.demo.application.common.exception;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class ErrorResponse {
	private Map<String, Object> params;
	private ErrorType code;
}
