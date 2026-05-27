package com.flynas.utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class ApiDataProvider {
    @DataProvider(name = "BookingFlowExcelData")
    public static Object[][] getExcelData() throws IOException {
        return ApiExcelReader.getSheetData("ApiTestData.xlsx","Booking");
    }
}
