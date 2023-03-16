package com.orangehrm;

import com.aventstack.extentreports.Status;
import com.orangehrm.pageFactory.util.propertyUtil;
import com.orangehrm.pageFactory.util.testBed;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class orangeHRMLoginWithDtPrverTest extends testBed {
    String email = propertyUtil.getProperty(getEnvFilePath(), "uname");
    String pwd = propertyUtil.getProperty(getEnvFilePath(), "pwd");

    @DataProvider(name = "loginWithDP")
    public Object[][] loginaDP() {
        return new Object[][]{{email, pwd}};
    }

    @Test(dataProvider = "loginWithDP", enabled = true, priority = 1)
    public void loginWithDaPrverTest(String email, String pwd, Method method)
            throws Exception {

        test = extent.createTest(method.getName());
        test.log(Status.INFO, "The thread ID for method: " + method.getName() + "browser: " + TestBedBrowser + " is " + Thread.currentThread().getId());

        objLogin.Login(email, pwd);
        objHome.clickLogout();
        Thread.sleep(3000);

    }
}

