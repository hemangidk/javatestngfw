package com.orangehrm.pageFactory;

import com.aventstack.extentreports.Status;
import com.orangehrm.pageFactory.util.testBed;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import com.orangehrm.pageFactory.util.basePage;


public class homePage extends basePage {
    objectRepository objRepo = new objectRepository(driver, mywait);

    public homePage(WebDriver driver, WebDriverWait mywait) {
        super(driver, mywait);
        // TODO Auto-generated constructor stub
    }

    protected String getHomeUsername()  {
        mywait.until(ExpectedConditions.visibilityOf(objRepo.capUserName));
//		System.out.println(capUserName.getText());
        return objRepo.capUserName.getText();
    }

    public void clickDrpDnButton() throws InterruptedException {
        Thread.sleep(3000);
        mywait.until(ExpectedConditions.visibilityOf(objRepo.drpdown));
        mywait.until(ExpectedConditions.elementToBeClickable(objRepo.drpdown));
        objRepo.drpdown.click();
//        new Actions(driver).moveToElement(objRepo.btnAcntDrpDn,1,1).click().build().perform();
        testBed.test.log(Status.PASS, "Clicked On DrpDnBtn");
    }


    public void clickLogout() throws InterruptedException {

        clickDrpDnButton();
        mywait.until(ExpectedConditions.visibilityOf(objRepo.btnLogout));
        objRepo.btnLogout.click();

        Reporter.log("clicked on logout Successful");
        testBed.test.log(Status.PASS, "clicked on logout Successful");

    }

}
