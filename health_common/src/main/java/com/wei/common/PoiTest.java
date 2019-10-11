package com.wei.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/11 14:39
 * @description:
 */
public class PoiTest {
    @Test
    public void demo() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("F:\\JavaSe\\Health\\2传智健康别的老师\\day05\\资料\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取行
        for (Row row : sheet) {
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.println("value = " + value);
            }
        }
    }
}
