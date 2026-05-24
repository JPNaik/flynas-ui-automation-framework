package com.flynas.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {
    private Logger log = LogManager.getLogger(TestListener.class);


    public void onTestStart(ITestResult result){
        log.info("TEST STARTED "+result.getMethod().getMethodName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("TEST PASSED "+result.getMethod().getMethodName());
    }
    public void onTestFailure(ITestResult result){
        String testName = result.getMethod().getMethodName();
        log.info("TEST FAILED "+testName);
        //Allure.step(testName);
        try {
            Object testClassInstance = result.getInstance();
            BaseUtils baseUtils = (BaseUtils) testClassInstance;
            byte[] screenShot = ((TakesScreenshot)baseUtils.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure Screenshot Evidence","image/png",
                    new ByteArrayInputStream(screenShot),
                    "png");
        } catch (Exception e) {
            log.error("Failed to capture screenshot within listener pipeline: " + e.getMessage(), e);
        }

    }

}
