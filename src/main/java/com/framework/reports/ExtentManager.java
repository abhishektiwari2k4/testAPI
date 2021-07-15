package com.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * This class creates the extent reports
 * 
 * @author abhishek.tewari
 *
 */
public class ExtentManager {

	public static final String runMode = "Test";

	private static ExtentReports extent;
	private static ExtentTest test;

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance("test-output/extent.html");
		return extent;
	}

	public static ExtentReports createInstance(String filename) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filename);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("API Automation Report");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Host Name", "Local");
		extent.setSystemInfo("Environment", runMode);
		return extent;
	}

	public static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		return test;
	}
}
