package com.flynas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiExcelReader {
    public static Logger log = LogManager.getLogger(ApiExcelReader.class);

    public static Object[][] getSheetData(String fileName,String sheetName) throws IOException {
        Object[][] data= null;
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "testdata" + File.separator + fileName;

        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getLastCellNum();

        data = new Object[rowCount][1];
        DataFormatter formatter = new DataFormatter();

        for(int i=0;i<rowCount;i++){
            Row currentRow = sheet.getRow(i+1);
            Map<String,String> map = new HashMap<>();
            for(int j=0;j<colCount;j++){
                String colName = formatter.formatCellValue(headerRow.getCell(j)).trim();
                String colValue = formatter.formatCellValue(currentRow.getCell(j)).trim();
                map.put(colName,colValue);
            }
            data[i][0]=map;
        }
        log.info("Successfully fetched data array matrix from Excel worksheet: {}", sheetName);
        return data;
    }
}
