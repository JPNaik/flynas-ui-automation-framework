package com.flynas.tests.ui;

import com.flynas.pages.SearchPage;
import com.flynas.utils.BaseUtils;
import com.flynas.utils.ExcelUtils;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class BookingTest extends BaseUtils {

    @DataProvider(name = "BookingDetails", parallel = true)
    public  Object[][] getExcelData(Method method) throws IOException {
        String[] parts = method.getName().split("_");
        String tcId = parts[0]+"_"+parts[1];
        return ExcelUtils.getTestData("Booking",tcId);
    }
    @Test(dataProvider = "BookingDetails")
    public void tc_01_onewayBooking(Map<String,String> testData)throws Exception{
        SearchPage searchPage = new SearchPage(getDriver());
        searchPage.acceptCookies();
        searchPage.selectTripType(testData.get("TripType"));
//        searchPage.selectOneWayRoute(testData.get("Route"));
//        searchPage.selectDate(testData.get("BookingDate"));
    }
    @Test(dataProvider = "BookingDetails")
    public void tc_02_roundTripBooking(Map<String,String> testData)throws Exception{
        SearchPage searchPage = new SearchPage(getDriver());
        searchPage.acceptCookies();
        searchPage.selectTripType(testData.get("TripType"));
//        searchPage.selectOneWayRoute(testData.get("Route"));
//        searchPage.selectDate(testData.get("BookingDate"));
    }
}
