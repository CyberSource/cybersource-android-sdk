package com.cybersource.inappsdk.common.utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

/*
 * @author fzubair
 */

public class SDKMathUtils {

	/** used to calculate the percentage **/
	private static BigDecimal HUNDRED = new BigDecimal("100");

	/** number of decimals returned **/
	private static int DECIMALS = 2;

	/**
	 * given an amount and a percentage, the method calculates the percentage
	 * using BigDecimal numbers
	 *
	 * @param amount
	 * @param percentage
	 * @return the percentage of the amount input
	 */
	public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
		if (isZero(percentage) || (amount == null)) {
			return ZERO;
		}

		BigDecimal fractionalChange = amount.multiply(percentage).divide(HUNDRED);

		return fractionalChange.setScale(DECIMALS, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * r checks if "number" equals zero
	 * 
	 * @param number
	 * @return true if number equals zero, false otherwise
	 */
	public static boolean isZero(BigDecimal number) {
		if (number == null) {
			number = BigDecimal.ZERO;
		}
		return number.compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * adds two BigDecimals and returns the total
	 * 
	 * @param number1
	 * @param number2
	 * @return total
	 */
	public static BigDecimal add(BigDecimal number1, BigDecimal number2) {
		if (number1 == null) {
			number1 = BigDecimal.ZERO;
		}
		if (number2 == null) {
			number2 = BigDecimal.ZERO;
		}
		return number1.add(number2).setScale(DECIMALS, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * substracts trwo BigDecimal numbers
	 * 
	 * @param number1
	 * @param number2
	 * @return result of the subtraction
	 */
	public static BigDecimal substract(BigDecimal number1, BigDecimal number2) {
		if (number1 == null) {
			number1 = BigDecimal.ZERO;
		}
		if (number2 == null) {
			number2 = BigDecimal.ZERO;
		}
		return number1.subtract(number2).setScale(DECIMALS, BigDecimal.ROUND_HALF_UP);
	}

}
