package me.ianhe.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author iHelin
 * @create 2017-02-17 18:06
 */
public class TestUtil {

    public static void main(String[] args) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("/Users/iHelin/test.xls"));
        //得到Excel工作簿对象
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        //得到Excel工作表对象
        HSSFSheet sheet = wb.getSheetAt(0);
        //得到Excel工作表的行
        HSSFRow row = sheet.getRow(1);
        //得到Excel工作表指定行的单元格
        HSSFCell cell = row.getCell((short) 1);
        HSSFCellStyle cellStyle = cell.getCellStyle();//得到单元格样式
    }

}
