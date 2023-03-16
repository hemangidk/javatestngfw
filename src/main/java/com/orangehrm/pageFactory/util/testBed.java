package com.orangehrm.pageFactory.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.orangehrm.pageFactory.homePage;
import com.orangehrm.pageFactory.loginPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.*;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.net.InetAddress.getLocalHost;

@Listeners({})
public abstract class testBed {
    public static final int TIMEOUT = 5;
    public  static String TestBedBrowser;
    public loginPage objLogin;
    public homePage objHome;
    private final String produrl = propertyUtil.getProperty(getEnvFilePath(), "URL");
    private final String extentReportName = getResultPath() + "/testReport_" + custUtil.getCurrentDateTimeStamp() + ".html";
    ExtentSparkReporter spark;
    public WebDriver driver;
    public WebDriverWait mywait;

    public static ExtentReports extent;
    //helps to generate the logs in the test report.
    public static ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite");
    }

    public void extentConfig()
    {
        spark = new ExtentSparkReporter(extentReportName);

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();

        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("UI Automation Report");
        spark.config().setReportName("Extent Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        spark.config().thumbnailForBase64();
        spark.config().setTimelineEnabled(true);

        extent.setSystemInfo("Env URL:", produrl);
        extent.setSystemInfo("Executed On Date: ", String.valueOf(new Date()));
        extent.setSystemInfo("executed by: ", System.getProperty("user.name"));
        try {
            extent.setSystemInfo("executed on Computer ip: ",getLocalHost().getHostAddress());
            extent.setSystemInfo("executed on Computer Name: ", getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        extent.attachReporter(spark);

    }
    @BeforeTest(alwaysRun = true)
    public void beforeTest(final ITestContext testContext) throws UnknownHostException {
        System.out.println("BeforeTest: Testcase start time stamp: " + custUtil.getCurrentDateTimeStamp());
        extentConfig();
    }

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(@Optional("chrome") String browser) {
//https://developers.perfectomobile.com/display/TT/How+to+pass+Chrome+options+as+
        System.out.println("BeforeClass: invoking browser:" + browser);
        TestBedBrowser = browser;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
//            options.addArguments("--headless");
//			options.addArguments("--disable-web-security");
//			options.addArguments("--allow-insecure-localhost");
//			options.addArguments("--ignore-urlfetcher-cert-requests")
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--incognito");
            options.addArguments("--start-maximized");
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
//			NOTE TESTED COMPLETELY
//			FirefoxOptions options = new FirefoxOptions();
//			options.addArguments("--incognito");
//			options.addArguments("--start-maximized");
//			WebDriverManager.edgedriver().setup();
//			driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("safari")) {
            SafariOptions options = new SafariOptions();
//            options.setUseTechnologyPreview(true);
//            System.out.println(options.getBrowserVersion());
            options.setCapability("safari.cleanSession", true);
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver(options);

        } else {
            Reporter.log("This Browser " + browser + "is not supported");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        mywait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        objLogin = new loginPage(getDriver(), getWebDriverWait());
        objHome = new homePage(getDriver(), getWebDriverWait());
    }



    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod time stamp: " + custUtil.getCurrentDateTimeStamp());

        Reporter.log("Launching URL : " + produrl);

        driver.get(produrl);

    }

    @AfterMethod
    public void tearDownMethod(ITestResult result)  {
        System.out.println("AfterMethod: Testcase end time stamp: " + custUtil.getCurrentDateTimeStamp());
        Reporter.setCurrentTestResult(result);

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String encodedBase64 = null;
        FileInputStream fileInputStreamReader;
        try {
            try {
                fileInputStreamReader = new FileInputStream(scrFile);
                byte[] bytes = new byte[(int) scrFile.length()];
                fileInputStreamReader.read(bytes);
                encodedBase64 = new String(Base64.encodeBase64(bytes));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sImageFile = "data:image/png;base64," + encodedBase64;

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "TimeStamp" + custUtil.getCurrentDateTimeStamp() + ":" + result.getName());
            test.log(Status.FAIL, result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(sImageFile).build());
            Reporter.log("Failure Time Stamp:" + custUtil.getCurrentDateTimeStamp(), true);
            Reporter.log(String.valueOf(MediaEntityBuilder.createScreenCaptureFromPath(sImageFile).build()));

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test execution completed for : " + result.getName());
        } else {
            test.log(Status.SKIP, "Test execution skipped for : " + result.getName());
        }
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

    @AfterSuite
    public void afterSuite(ITestContext itstCntxt ) {
        System.out.println("AfterSuite: Emailing Report");
        List<String> summary =new ArrayList<String>();

        summary.add("\n");
        summary.add("**************");
        summary.add("\n");
        summary.add("** Test Execution Summary **");
        summary.add("\n");
        summary.add("**************");
        summary.add("\n");
        summary.add("Pass : " + itstCntxt.getPassedTests().size());
        summary.add("\n");
        summary.add("Fail : " + itstCntxt.getFailedTests().size());
        summary.add("\n");
        summary.add("Skipped : " + itstCntxt.getSkippedTests().size());
        summary.add("\n");
        summary.add("**************");
        summary.add("\n");
        summary.add("NOTE: This is system generated email. plz do not reply. If any questions please let me know at wfhuser2@gmail.com");
        summary.add("\n");
        custUtil.sendEmailReport(extentReportName , summary);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWebDriverWait() {
        return mywait;
    }

    public static String getEnvFilePath() {
        String filename = "";
        try {

            filename = new java.io.File(".").getCanonicalPath() + "/config/dev_env.properties";
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            Assert.fail("Not able to locate: " + filename);

        }
        return filename;
    }

    protected static String getResultPath() {
        return System.getProperty("user.dir") + "/Results";
    }
}