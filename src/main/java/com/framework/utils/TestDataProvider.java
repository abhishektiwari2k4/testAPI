package com.framework.utils;

import org.testng.annotations.DataProvider;

/**
 * This class contains data provider values
 * 
 * @author abhishek.tewari
 *
 */
public class TestDataProvider {

	@DataProvider
	public static Object[][] invalidEmail() {
		return new Object[][] { { "aa" }, { "aa@" }, { "aa@g" }, { "aa@gmail" }, { "aa@.com" }, { "@gmail.com" },
				{ "@.com" } };
	}

	@DataProvider
	public static Object[][] nullBlank() {
		return new Object[][] { { null }, { "" } };
	}

	@DataProvider
	public static Object[][] invalidFormat() {
		return new Object[][] { { "MM-dd-yyyy" }, { "dd-MM-yyyy" }, { "dd-MM-yy" }, { "MM-dd-yy" }, { "yyyy" } };
	}

	@DataProvider
	public static Object[][] firstNameLength() {
		return new Object[][] { { 1 } };
	}

	@DataProvider
	public static Object[][] lastNameLength() {
		return new Object[][] { { 1 }, { 31 }, { 60 } };
	}

	@DataProvider
	public static Object[][] invalidId() {
		return new Object[][] { { -1 }, { 0 } };
	}
}