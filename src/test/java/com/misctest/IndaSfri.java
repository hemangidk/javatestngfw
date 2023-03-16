package com.misctest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


import static org.testng.Assert.assertEquals;

public class IndaSfri {
    public static ExtentTest test;
    public static String url = "https://www.techlistic.com/p/demo-selenium-practice.html";
    public static WebDriver driver;

    public static ExtentReports extent;
    @Test()
    public void CompareTableData(Method method) {
        test = extent.createTest(method.getName());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(10));
        driver.get(url);

        HashMap<String, String> map = new HashMap<String, String>();//Creating HashMap
        HashMap<String, String> map2 = new HashMap<String, String>();//Creating HashMap

        map.put("Google","Germany");
        map.put("Meta","Mexico");
        map.put("Microsoft","Austria");
        map.put("Island Trading","UK");
        map.put( "Adobe","Canada");
        map.put( "Amazon","Italy");


        String xpComp1 = "//div//table[@id=\"customers\"]//tr";
        int len = driver.findElements(By.xpath(xpComp1)).size();
        for (int i = 2; i <= len; i++) {
            String Col1 = driver.findElement((By.xpath(("//div//table[@id=\"customers\"]//tr[" + i + "]/td[1]")))).getText();
            System.out.println(Col1);
            String Col3 = driver.findElement((By.xpath(("//div//table[@id=\"customers\"]//tr[" + i + "]/td[3]")))).getText();
            System.out.println(Col3);
            map2.put(Col1, Col3);
        }
        assertEquals(map,map2);

    }

    @AfterMethod
    public void tearDownMethod(ITestResult result)  {
        System.out.println("AfterMethod: Testcase end time stamp: " + getCurrentDateTimeStamp());

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "TimeStamp" + getCurrentDateTimeStamp() + ":" + result.getName());
            Reporter.log("Failure Time Stamp:" + getCurrentDateTimeStamp(), true);


        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test execution completed for : " + result.getName());
        } else {
            test.log(Status.SKIP, "Test execution skipped for : " + result.getName());
        }
    }
    protected static String getResultPath() {
        return System.getProperty("user.dir") ;
    }
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite");
    }
    private final String extentReportName = getResultPath() + "/Results/testReport_" + getCurrentDateTimeStamp() + ".html";
    @BeforeTest(alwaysRun = true)
    public void beforeTest() throws UnknownHostException {

        System.out.println("BeforeTest: Testcase start time stamp: " + getCurrentDateTimeStamp());

        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportName);

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();

        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Simple Automation Report");
        spark.config().setReportName("Table Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        spark.config().thumbnailForBase64();
        spark.config().setTimelineEnabled(true);

        extent.setSystemInfo("Env URL:", url);
        extent.setSystemInfo("Executed On Date: ", String.valueOf(new Date()));
        extent.setSystemInfo("executed by: ", System.getProperty("user.name"));
        extent.setSystemInfo("executed on Computer ip: ", java.net.InetAddress.getLocalHost().getHostAddress());
        extent.setSystemInfo("executed on Computer Name: ", java.net.InetAddress.getLocalHost().getHostName());
        extent.attachReporter(spark);

    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod: Testcase start time stamp: " + getCurrentDateTimeStamp());


    }
    @AfterTest
    public void tearDownTest() {
        //to write or update test information to reporter
        System.out.println("AfterTest:");
        extent.flush();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("AfterClass:");
        if (null != driver) {
            driver.close();
            driver.quit();
        }
    }

    public static String getCurrentDateTimeStamp() {
        Date objDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss");
        objDate = new Date();
        // Current System Date and time is assigned to objDate
        return (sdf.format(objDate));
    }

}
