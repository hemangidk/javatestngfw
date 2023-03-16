package com.orangehrm.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.pageFactory.util.basePage;

public class yopmail extends basePage {


    public yopmail(WebDriver driver, WebDriverWait mywait) {
        super(driver, mywait);
        // TODO Auto-generated constructor stub
    }

    By btnYopmailHome = By.id("ycptcpt");

    @FindBy(id = "login")
    WebElement txtYopmailEmail;
    @FindBy(xpath = "//div[@id='refreshbut']/button/i")
    WebElement btnGoToEmail;

    public void openYopMailAndOpenEmail(String email, String Subject) throws Exception {
            driver.get("https://yopmail.com/en/");
            driver.findElement(btnYopmailHome).click();
            txtYopmailEmail.click();
//        driver.findElement(By.id("login")).click();
            //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | id=login | ]]
            txtYopmailEmail.clear();
            txtYopmailEmail.sendKeys(email);//"alphaencyro03@yopmail.com");
            btnGoToEmail.click();
            Thread.sleep(3000);
            driver.switchTo().frame("ifinbox");

//            linkFirstEmail.click();
            By emailsub = By.xpath("//button[@class=\"lm\"]//div[text()='"+Subject+"']");
            driver.findElement(emailsub).click();
            driver.switchTo().defaultContent();
            Thread.sleep(5000);

            driver.switchTo().frame("ifmail");
        }


}

