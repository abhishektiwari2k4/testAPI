package com.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class contains common utility methods
 * 
 * @author abhishek.tewari
 *
 */
public class CommonUtil {

	/**
	 * Get date in a specified format after a given period
	 * 
	 * @param days
	 * @param format
	 * @return String
	 */
	public String getDate(int days, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * Convert string to date in a given format
	 * 
	 * @param date
	 * @param format
	 * @return Date
	 * @throws ParseException
	 */
	public Date convertToDate(String date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(date);
	}

	/**
	 * Get random email
	 * 
	 * @return String
	 */
	public String getRandomEmail() {
		return "email" + RandomStringUtils.randomNumeric(10) + "@gmail.com";
	}

	/**
	 * Get random string of specified length
	 * 
	 * @param length
	 * @return String
	 */
	public String getRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}
}
