package com.flynas.utils;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class GlobalSetupListener implements ISuiteListener {

    public void onStart(ISuite suite)
    {
        String xmlEnv = suite.getParameter("env");
        if(xmlEnv!=null)
        {
            System.setProperty("env",xmlEnv.trim().toLowerCase());
        }
    }
}
