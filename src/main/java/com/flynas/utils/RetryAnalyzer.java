package com.flynas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private Logger log = LogManager.getLogger(RetryAnalyzer.class);

    private int retryCount=0;
    private int MAX_RETRIES=1;
    @Override
    public boolean retry(ITestResult result) {
        if(retryCount<MAX_RETRIES){
            retryCount++;
            log.info("Test retry "+result.getMethod().getMethodName());
            return true;
        }
        else{
            return  false;
        }
    }
}
