package com.orangehrm.pageFactory.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class basePage {

    protected WebDriver driver;
    protected WebDriverWait mywait;
    protected basePage(WebDriver driver, WebDriverWait mywait) {
        this.driver = driver;
        this.mywait = mywait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, testBed.TIMEOUT), this);
    }

}
