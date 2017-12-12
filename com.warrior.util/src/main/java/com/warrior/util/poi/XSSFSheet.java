package com.warrior.util.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFSheet extends ExcelSheet{

    @Override
    public void genSheet() {
        XSSFWorkbook wb = (XSSFWorkbook)workbook;

        Sheet sheet = wb.createSheet(title);
        Row row = sheet.createRow(0);
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.LIME.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        row.setHeight((short)500);
        createHeaders(sheet,row,cellStyle);

        XSSFCellStyle dataCellStyle = wb.createCellStyle();
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        int i = 1;
        if(data != null && data.size() > 0){
            for (Object [] item:data) {
                createRow(sheet,item,dataCellStyle,i++);
            }
        }
        if(otherData != null && otherData.size() > 0) {
            for (Object[] item : otherData) {
                createRow(sheet, item, cellStyle, i++);
            }
        }
    }

}
