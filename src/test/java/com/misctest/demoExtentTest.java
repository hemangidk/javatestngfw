package com.misctest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class demoExtentTest {

    ExtentReports extent;
    //helps to generate the logs in the test report.
    ExtentTest test;

    @BeforeTest
    public void startReport() {
        // initialize the HtmlReporter

        ExtentSparkReporter spark  = new ExtentSparkReporter(System.getProperty("user.dir") +"/test-output/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();

        //configuration items to change the look and feel
        //add content, manage tests etc
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Simple Automation Report");
        spark.config().setReportName("Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent.attachReporter(spark);
    }

    @Test
    public void test_1() {
        test = extent.createTest("Test Case 1", "The test case 1 has passed");
        Assert.assertTrue(true);
    }


    @Test
    public void test_2() {
        test = extent.createTest("Test Case 2", "The test case 2 has failed");
        Assert.assertTrue(false);
    }

    @Test
    public void test_3() {
        test = extent.createTest("Test Case 3", "The test case 3 has been skipped");
        throw new SkipException("The test has been skipped");
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getTestName());
        }
        else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }
}