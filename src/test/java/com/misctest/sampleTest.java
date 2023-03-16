package com.misctest;

import com.orangehrm.pageFactory.util.testBed;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class sampleTest extends testBed {

    @Test(enabled=false,priority = 1, description = "Invalid")
    public void level1(Method method)
    {
//        startTest(method.getName(), "Invalid Login Scenario with empty username and password.");

        Reporter.log("level1",1);
    }
    @Test(enabled=true)
    //	@Parameters("myName")
    public void level2(Method method)
    {
        test = extent.createTest(method.getName());
        Reporter.log("level2",2);
    }
    @Test(enabled=true)
    //	@Parameters("myName")
    public void level3(Method method)
    {
        test = extent.createTest(method.getName());
        Reporter.log("level3",3);
    }
    @Test
    //	@Parameters("myName")
    public void level4(Method method)
    {
        test = extent.createTest(method.getName());
//        Assert.assertTrue(false);
        Assert.fail("Induced failure for testing: "+method.getName());
    }
}
