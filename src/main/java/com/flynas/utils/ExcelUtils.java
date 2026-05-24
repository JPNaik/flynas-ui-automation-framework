package com.flynas.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    public static Object[][] getTestData(String sheetName,String testCaseId) throws IOException {
        String filePath = System.getProperty("user.dir")+ File.separator+"src"+File.separator+
                "test"+File.separator+"resources"+File.separator+"TestData.xlsx";
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet= workbook.getSheet(sheetName);

        int rowCount=sheet.getPhysicalNumberOfRows();
        Row headerRow = sheet.getRow(0);
        int colCount=headerRow.getLastCellNum();

        List<Map<String,String>> matchingRowList = new ArrayList<>();

        for(int i=1;i<rowCount;i++){
            Row currentRow = sheet.getRow(i);
            if(currentRow==null){
                continue;
            }
            String currentTc_id = currentRow.getCell(0).getStringCellValue().trim();
            String executionFlag = currentRow.getCell(1).getStringCellValue().trim();

            if(currentTc_id.equalsIgnoreCase(testCaseId)&&executionFlag.equalsIgnoreCase("Y")){
                Map<String,String> dataMap = new HashMap<>();
                for(int j=0;j<colCount;j++){
                    String colName=headerRow.getCell(j).getStringCellValue().trim();
                    String cellValue=currentRow.getCell(j).getStringCellValue().trim();
                    dataMap.put(colName,cellValue);
                }
                matchingRowList.add(dataMap);
            }
        }
        workbook.close();
        file.close();

        Object[][] testData = new Object[matchingRowList.size()][1];
        for(int i=0;i<matchingRowList.size();i++){
            testData[i][0] = matchingRowList.get(i);
        }
        return testData;
    }
}
