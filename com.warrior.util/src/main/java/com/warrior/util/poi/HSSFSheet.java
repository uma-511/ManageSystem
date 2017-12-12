package com.warrior.util.poi;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class HSSFSheet extends ExcelSheet {

    public void genSheet(){
        HSSFWorkbook wb = (HSSFWorkbook)workbook;
        Sheet sheet = wb.createSheet(title);
        Row row = sheet.createRow(0);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.LIME.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(IndexedColors.LIME.index,(byte)217,(byte)217,(byte)217);

        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        row.setHeight((short)500);
        createHeaders(sheet,row,cellStyle);
        HSSFCellStyle dataCellStyle = wb.createCellStyle();
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
