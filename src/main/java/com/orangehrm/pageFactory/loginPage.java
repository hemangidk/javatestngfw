package com.orangehrm.pageFactory;

import com.aventstack.extentreports.Status;
import com.orangehrm.pageFactory.util.custUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import com.orangehrm.pageFactory.util.testBed;
import com.orangehrm.pageFactory.util.basePage;


public class loginPage  extends basePage {

    homePage objHome = new homePage(driver, mywait);
    objectRepository objRepo = new objectRepository(driver, mywait);

    public loginPage(WebDriver driver, WebDriverWait mywait) {
        super(driver, mywait);
        // TODO Auto-generated constructor stub
    }


    public void setUserName(String sName) {

        mywait.until(ExpectedConditions.visibilityOf(objRepo.txtUsername));
        objRepo.txtUsername.clear();
        objRepo.txtUsername.sendKeys(sName);
    }

    // Set password in password textbox
    public void setPassword(String sPwd) {
        mywait.until(ExpectedConditions.visibilityOf(objRepo.txtPassword));
        objRepo.txtPassword.clear();
        objRepo.txtPassword.sendKeys(sPwd);
    }

    public void clickLogin() {
        mywait.until(ExpectedConditions.visibilityOf(objRepo.btnLogin));
        mywait.until(ExpectedConditions.elementToBeClickable(objRepo.btnLogin));
        objRepo.btnLogin.click();

    }


    public void Login(String sName, String pwd) throws InterruptedException {
//		driver.navigate().refresh();
        this.setUserName(sName);
        this.setPassword(pwd);
        this.clickLogin();

        //div[@class="initspinner"]
//        mywait.until(ExpectedConditions.invisibilityOfElementLocated(objRepo.spinner));

        try {
            Thread.sleep(5000);

                objRepo.capUserName.isDisplayed();
                System.out.println(objHome.getHomeUsername());
                testBed.test.log(Status.PASS, objHome.getHomeUsername());
                Reporter.log("login successful for user: " + sName);

                custUtil.captureScreenShot(driver, "After Login");
        } catch (Exception e) {
            try {

                    if (!objRepo.capUserName.isDisplayed()) {
                        Assert.fail("UserName Not displayed on Login");
                    } else {
                        Assert.fail("Login not successful");
                    }
             } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        mywait.until(ExpectedConditions.elementToBeClickable(objRepo.drpdown));
    }


}
