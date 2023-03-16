package com.orangehrm;

import com.aventstack.extentreports.Status;
import com.orangehrm.pageFactory.util.excelUtil;
import com.orangehrm.pageFactory.util.testBed;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class orangeHRMLoginWithExclDaPrverTest extends testBed {

    @DataProvider(name = "loginWithExlDP")
    public Object[][] loginaDP() {
        try {
            System.out.println(new java.io.File(".").getCanonicalPath());
            return excelUtil.ReadDataFromExcelFile(new java.io.File(".").getCanonicalPath() + "\\Data\\userData.xlsx", "data");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "loginWithExlDP", enabled = true, priority = 1)
    public void loginWithExclDaPrver(String email, String pwd, Method method)
            throws Exception {
        test = extent.createTest(method.getName());
        test.log(Status.INFO, "The thread ID for method: " + method.getName() + "browser: " + TestBedBrowser + " is " + Thread.currentThread().getId());

        objLogin.Login(email, pwd);
        objHome.clickLogout();
        Thread.sleep(3000);
    }
}

