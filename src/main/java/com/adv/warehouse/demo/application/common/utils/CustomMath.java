package com.adv.warehouse.demo.application.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomMath {

	public static double round(double value) {
		BigDecimal decimal = new BigDecimal(Double.toString(value));
		decimal = decimal.setScale(2, RoundingMode.HALF_UP);
		return decimal.doubleValue();
	}
}
