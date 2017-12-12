package com.warrior.util.poi;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelWorkBook {

    @Getter
    private ExcelType excelType = ExcelType.EXCEL_TYPE_XLS;
    private Workbook wb;
    private List<ExcelSheet> sheetList;

    public ExcelWorkBook(ExcelType excelType) {
        this.excelType = excelType;
        wb = excelType == ExcelType.EXCEL_TYPE_XLSX ? new XSSFWorkbook() : new HSSFWorkbook();
    }

    public ExcelWorkBook() {
    }

    /**
     * 设置excel类型 03 or 07
     * @param type
     * @return
     */
    public ExcelWorkBook setExcelType(ExcelType type) {
        this.excelType = type;
        wb = excelType == ExcelType.EXCEL_TYPE_XLSX ? new XSSFWorkbook() : new HSSFWorkbook();
        return this;
    }

    /**
     * 添加Excel sheet
     * @param title
     * @param headers
     * @return
     */
    public ExcelSheet addSheet(String title, String headers) {
        if (sheetList == null) {
            sheetList = Lists.newArrayList();
        }
        ExcelSheet sheet = (excelType == ExcelType.EXCEL_TYPE_XLSX ? new XSSFSheet() : new HSSFSheet());
        sheet.setWorkbook(wb == null ? new HSSFWorkbook() : wb);
        sheet.setTitle(title);
        sheet.addHeader(headers);
        sheetList.add(sheet);
        return sheet;
    }

    /**
     * 生成Excel文件
     * @param out
     * @throws IOException
     */
    public void genExcel(OutputStream out) throws IOException {
        sheetList.forEach(item -> item.genSheet());
        wb.write(out);
    }

    /**
     * 生成Excel文件
     * @param filePath
     * @throws IOException
     */
    public void genExcel(String filePath) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        sheetList.forEach(item -> item.genSheet());
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 读取Excel文件
     * @param inp
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelWorkBook readExcel(InputStream inp) throws InvalidFormatException, IOException {
        this.wb = WorkbookFactory.create(inp);
        return this;
    }

    /**
     * 读取Excel文件
     * @param filePath
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public ExcelWorkBook readExcel(String filePath) throws IOException,InvalidFormatException{
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException();
        }
        this.wb = WorkbookFactory.create(file);
        return this;
    }

    /**
     * 读取sheet
     * @param index
     * @param hasTitle
     * @return
     */
    public List<Object []> readSheet(int index,boolean hasTitle){
        List<Object []> list = Lists.newArrayList();
        Sheet sheet = wb.getSheetAt(index);
        int start = sheet.getFirstRowNum();
        int end = sheet.getLastRowNum();
        Row row;
        Object [] temp;
        for (int i=start;i <= end;i++){
            if(hasTitle && i == 0){
                continue;
            }
            row = sheet.getRow(i);
            temp = new Object[row.getLastCellNum()];
            for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                temp[j] = getValue(row.getCell(j));
            }
            list.add(temp);
        }
        return list;
    }

    private Object getValue(Cell cell){
        Object obj;
        CellType type = cell.getCellTypeEnum();
        if(type == CellType.BOOLEAN){
            obj = cell.getBooleanCellValue();
        } else if(type == CellType.NUMERIC){
            obj = cell.getNumericCellValue();
        } else if(type == CellType.STRING){
            obj = cell.getStringCellValue();
        } else if(type == CellType.FORMULA){
            obj = cell.getArrayFormulaRange();
        } else if(type == CellType.BLANK){
            obj = null;
        } else{
            obj = cell.getStringCellValue();
        }
        return obj;
    }
}
