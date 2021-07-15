package com.framework.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.framework.constants.RestUri;
import com.framework.reports.ExtentManager;
import com.framework.utils.CommonUtil;
import com.framework.utils.RestUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * This class contains before suite and after suite initialization
 * 
 * @author abhishek.tewari
 *
 */
public class BaseTest {

	public RequestSpecification request;
	public RequestSpecBuilder requestSpecBuilder;
	public ExtentReports extent = ExtentManager.getInstance();
	public ExtentTest test;
	public RestUtil rest;
	public CommonUtil util;

	@BeforeSuite
	public void beforeSuite() throws Exception {
		RestAssured.baseURI = RestUri.BASE_URI;
	}

	@AfterSuite
	public void logout() throws Exception {
		System.out.println("After Suite Executing");
		extent.flush();
	}
}