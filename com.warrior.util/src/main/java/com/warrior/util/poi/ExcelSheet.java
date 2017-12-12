package com.warrior.util.poi;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public abstract class ExcelSheet {

    @Setter @Getter
    protected Workbook workbook;
    //sheet标题
    @Setter @Getter
    protected String title;

    @Setter @Getter
    protected String [] headers;

    @Setter @Getter
    protected List<Object[]> data = Lists.newArrayList();

    @Setter @Getter
    protected List<Object[]> otherData = Lists.newArrayList();


    public abstract void genSheet();

    /**
     * 添加表头 多个表头已 , 隔开
     * @param header
     * @return
     */
    public ExcelSheet addHeader(String header){
        this.headers = header.split(",");
        return this;
    }

    public ExcelSheet addData(Object [] rowData){
        this.data.add(rowData);
        return this;
    }
    public ExcelSheet addData(List<Object[]> rowData){
        if(rowData != null && rowData.size() > 0){
            this.data.addAll(rowData);
        }
        return this;
    }
    public ExcelSheet addOtherData(Object [] rowData){
        this.otherData.add(rowData);
        return this;
    }

    protected void createHeaders(Sheet sheet,Row row,CellStyle cellStyle){
        if (headers!=null){
            Cell cell;
            for (int i=0,j= headers.length;i<j;i++) {
                sheet.setColumnWidth(i,5000);
                cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    protected Row createRow(Sheet sheet, Object [] item, CellStyle dataCellStyle, int i){
        Row row = sheet.createRow(i);
        Cell cell;
        row.setHeight((short)500);
        for (int j=0,k=item.length;j<k;j++) {
            cell = row.createCell(j);
            cell.setCellStyle(dataCellStyle);
            if (item[j] instanceof String){
                cell.setCellValue(String.valueOf(item[j]));
                cell.setCellType(CellType.STRING);
            }else if(item[j] instanceof Integer || item[j] instanceof Double){
                cell.setCellValue(Double.parseDouble(item[j].toString()));
                cell.setCellType(CellType.NUMERIC);
            }else{
                cell.setCellValue(String.valueOf(item[j]));
                cell.setCellType(CellType.STRING);
            }
        }
        return row;
    }
}
