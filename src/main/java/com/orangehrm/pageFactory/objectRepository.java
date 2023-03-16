package com.orangehrm.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.pageFactory.util.basePage;


public class objectRepository extends basePage {
    protected WebDriver driver;
    protected WebDriverWait mywait;

    public objectRepository(WebDriver driver, WebDriverWait mywait) {
        super(driver, mywait);
        // TODO Auto-generated constructor stub
    }
    //--------LOGIN SCREEN----

    public @FindBy(xpath = "//div//button[@type=\"submit\"]") ////button[@type=\"submit\" and text()=' Login ']")
    WebElement btnLogin;

    public @FindBy(xpath = "//input[@name=\"username\"]")
    WebElement txtUsername;

    @FindBy(xpath = "//input[@name=\"password\"]")
    WebElement txtPassword;

    @FindBy(xpath = "//div//ul//li//p[@class=\"oxd-userdropdown-name\"]")
    WebElement capUserName;

    @FindBy(xpath = "//div//ul//li//i[@class=\"oxd-icon bi-caret-down-fill oxd-userdropdown-icon\"]")
    WebElement drpdown;

    @FindBy(xpath ="//li/a[@href=\"/web/index.php/auth/logout\"]" )//"//ul//li/a[text()='Logout']")
    WebElement btnLogout;

    public By EsignRqstSnt = By.xpath("//div//strong[text()='E-Sign Request Sent']");

}
