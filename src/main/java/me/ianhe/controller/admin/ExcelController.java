package me.ianhe.controller.admin;

import me.ianhe.controller.BaseController;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author iHelin
 * @create 2017-02-17 19:47
 */
@Controller
public class ExcelController extends BaseController {

    @RequestMapping(value = "excel")
    public void exportExcel(HttpServletResponse response) {
        // 生成提示信息，
        response.setContentType("application/vnd.ms-excel");
        String codedFileName;
        OutputStream fOut = null;
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            // 进行转码，使其支持中文文件名
            codedFileName = URLEncoder.encode("test", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            //产生工作表对象
            HSSFSheet sheet = workbook.createSheet("工资表");
            sheet.setDefaultColumnWidth(10);
            sheet.setDefaultRowHeight((short) 400);
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0 = row0.createCell(0, CellType.STRING);
            HSSFFont font = workbook.createFont();
            font.setFontName("微软雅黑");
            font.setBold(true);//粗体显示
            font.setFontHeightInPoints((short) 16);
            hssfCellStyle.setFont(font);
            hssfCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell0.setCellStyle(hssfCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 9));
            cell0.setCellValue("2017年01月工资表");
            HSSFCellStyle hlinkStyle = workbook.createCellStyle();
            HSSFFont hlinkFont = workbook.createFont();
            hlinkFont.setUnderline(HSSFFont.U_SINGLE);
            hlinkFont.setColor(HSSFColor.BLUE.index);
            hlinkStyle.setFont(hlinkFont);
            for (int i = 1; i <= 20; i++) {
                HSSFRow row = sheet.createRow(i);//创建一行
                HSSFCell cell = row.createCell(0);//创建一列
                HSSFCreationHelper helper = workbook.getCreationHelper();
                Hyperlink link = helper.createHyperlink(HyperlinkType.DOCUMENT);
                link.setAddress("#赖玉霞!A1");
                cell.setCellType(CellType.STRING);
                cell.setCellValue("test" + i);
                cell.setHyperlink(link);
                cell.setCellStyle(hlinkStyle);
//                cell.setCellFormula("HYPERLINK(\"[test.xls]'赖玉霞'!A1\",\"homepage\")");
            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
        } catch (Exception e) {
        }
        System.out.println("文件生成...");
    }

}
