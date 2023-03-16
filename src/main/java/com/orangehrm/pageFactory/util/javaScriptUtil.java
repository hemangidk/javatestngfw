package com.orangehrm.pageFactory.util;

import java.io.IOException;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class javaScriptUtil {
//	public WebDriver driver;
	
	public void execute_js(WebDriver driver,String Command, WebElement ele){
		try
		{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(Command,ele);
		Thread.sleep(2000);
		}
		catch (Exception e)
		{
			Assert.fail("Not able to execute script : " + e);
		}
	}
	
	public void execute_js(WebDriver driver,String Command ){
		try {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(Command);
		}
		catch (Exception e)
		{
			Assert.fail("Not able to execute reason : " + e);
		}
	}
	@Test
	public void SampleExample() throws IOException, InterruptedException {
		EdgeOptions options = new EdgeOptions();
		options.addArguments("--incognito");
		options.addArguments("--start-maximized");
//        options.addArguments("--start-minimized");
		WebDriverManager.edgedriver().setup();
		WebDriver driver = (WebDriver) new EdgeDriver(options);

		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(10));
		// Launching the Site.
		driver.get("http://demo.guru99.com/V4/");

		WebElement button = driver.findElement(By.name("btnLogin"));

		// Login to Guru99
		driver.findElement(By.name("uid")).sendKeys("mngr34926");
		driver.findElement(By.name("password")).sendKeys("amUpenu");
		execute_js(driver,"arguments[0].click();", button);
		Thread.sleep(2000);
		Actions builder = new Actions(driver);
		Action ok = builder.sendKeys(Keys.SPACE).build();
		ok.perform();
		// Perform Click on LOGIN button using JavascriptExecutor
		

		// To generate Alert window using JavascriptExecutor. Display the alert message
		execute_js(driver,"alert('Welcome to Guru99');");
		execute_js(driver,"window.scrollBy(0,600)");
		

	}
}