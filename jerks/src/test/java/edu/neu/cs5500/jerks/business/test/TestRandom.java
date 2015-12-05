package edu.neu.cs5500.jerks.business.test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/* Author: Karthik Chandranna
 * Creation Date: 11/23/2015 9:07 PM EST
 * Description: Random generators
 * */
public class TestRandom extends Random {

	private static final long serialVersionUID = 1L;
	private static final String ALPHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMBERS = "0123456789";
	private static final String ALPHA_NUMERIC = ALPHA_CHARS + NUMBERS;

	public char nextChar(final String string) {
		return string.charAt(nextInt(string.length()));
	}

	public String nextStr(final String chars, final int length) {
		final StringBuilder s = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			s.append(nextChar(chars));
		}
		return s.toString();
	}

	public String nextStr(final int length) {
		return nextStr(ALPHA_CHARS, length);
	}

	public String nextAlphaNumStr(final int length) {
		return nextStr(ALPHA_NUMERIC, length);
	}

	public String nextNum(final int length) {
		return nextStr(NUMBERS, length);
	}

	public float nextFloat(final int beforeDecimal, final int precision) {
		final String beforeDecimalStr = nextNum(beforeDecimal);
		final String precisionStr = nextNum(precision);
		final String result = beforeDecimalStr + "." + precisionStr;
		return Float.parseFloat(result);
	}

	public double nextDouble(final int beforeDecimal, final int precision) {
		final String beforeDecimalStr = nextNum(beforeDecimal);
		final String precisionStr = nextNum(precision);
		final String result = beforeDecimalStr + "." + precisionStr;
		return Double.parseDouble(result);
	}

	public String nextEmail() {
		return nextStr(10) + "@" + nextStr(7) + ".com";
	}

	public Date nextDate() {

		Random rand = new Random();
		int month = rand.nextInt((12 - 1) + 1) + 1;
		int year = rand.nextInt((2010 - 1950) + 1) + 1950;
		int day = rand.nextInt((30 - 1) + 1) + 1;

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		return calendar.getTime();
	}

}
