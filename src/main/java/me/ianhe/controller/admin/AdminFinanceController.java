package me.ianhe.controller.admin;

import com.beust.jcommander.internal.Lists;
import me.ianhe.db.entity.Activity;
import me.ianhe.db.entity.Staff;
import me.ianhe.db.plugin.Pagination;
import me.ianhe.utils.DateTimeUtil;
import org.apache.commons.lang3.CharEncoding;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 财务Controller
 *
 * @author iHelin
 * @create 2017-02-18 12:16
 */
@Controller
public class AdminFinanceController extends BaseAdminController {

    @RequestMapping(value = "finance", method = RequestMethod.GET)
    public String financePage(Model model, Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        List<Staff> staffs = financeManager.listStaffByCondition(
                (pageNum - 1) * DEFAULT_PAGE_LENGTH, DEFAULT_PAGE_LENGTH);
        int totalCount = financeManager.listStaffCount();
        model.addAttribute("staffs", staffs);
        model.addAttribute("pagination", new Pagination(totalCount, pageNum, DEFAULT_PAGE_LENGTH));
        return ftl("finance");
    }

    @RequestMapping(value = "finance/staff/{staffId}", method = RequestMethod.GET)
    public String staffDetail(Model model, @PathVariable Integer staffId, Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        Staff staff = financeManager.getStaffById(staffId);
        List<Activity> activities = financeManager.listActivityByCondition(staffId,
                (pageNum - 1) * DEFAULT_PAGE_LENGTH, DEFAULT_PAGE_LENGTH);
        int totalCount = financeManager.listActivityCount(staffId);
        model.addAttribute("staff", staff);
        model.addAttribute("activities", activities);
        model.addAttribute("pagination", new Pagination(totalCount, pageNum, DEFAULT_PAGE_LENGTH));
        return ftl("staff_detail");
    }

    /**
     * 添加、修改员工
     *
     * @param staff
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "staff", method = RequestMethod.POST)
    public String addStaff(@RequestBody Staff staff) {
        if (staff.getId() != null) {
            financeManager.updateStaff(staff);
        } else {
            financeManager.addStaff(staff);
        }
        return success();
    }

    /**
     * 添加或修改活动
     *
     * @param activity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "activity", method = RequestMethod.POST)
    public String addActivity(Activity activity, String dateStr) {
        Date date = DateTimeUtil.parseDate(dateStr);
        activity.setDate(date);
        if (activity.getId() != null) {
            financeManager.updateActivity(activity);
        } else {
            financeManager.addActivity(activity);
        }
        return success();
    }


    /**
     * 删除员工
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "staff/{id}", method = RequestMethod.DELETE)
    public String deleteStaff(@PathVariable Integer id) {
        financeManager.removeStaff(id);
        return success();
    }

    /**
     * 删除活动
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "activity/{id}", method = RequestMethod.DELETE)
    public String deleteActivity(@PathVariable Integer id) {
        financeManager.removeActivity(id);
        return success();
    }

    /**
     * 通过员工id获取员工
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "staff/{id}", method = RequestMethod.GET)
    public String getStaffById(@PathVariable Integer id) {
        Staff staff = financeManager.getStaffById(id);
        return success(staff);
    }

    /**
     * 通过活动id获取活动
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "activity/{id}", method = RequestMethod.GET)
    public String getActivityById(@PathVariable Integer id) {
        Activity activity = financeManager.getActivityById(id);
        return success(activity);
    }

    /**
     * 工资单导出
     *
     * @param response
     */
    @RequestMapping(value = "excel")
    public void exportExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        // 生成提示信息，
        response.setContentType("application/vnd.ms-excel");
        LocalDate localDate = LocalDate.now();
        String fileName = localDate.getYear() + "年" + localDate.getMonthValue() + "月工资表";
        OutputStream fOut = null;
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, CharEncoding.UTF_8) + ".xls");
            //设置居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            //创建字体
            HSSFFont font = workbook.createFont();
            font.setFontName("微软雅黑");
            font.setBold(true);//粗体显示
            font.setFontHeightInPoints((short) 16);

            //应用字体，设置背景，第一行通用样式
            hssfCellStyle.setFont(font);
            hssfCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //产生工作表对象
            List<Staff> staffs = financeManager.listStaffByCondition();
            Sheet firstSheet = buildFirstSheet(workbook, fileName, hssfCellStyle, staffs);
            List<HSSFSheet> staffSheets = createStaffSheets(staffs, workbook);
            fOut = response.getOutputStream();
            workbook.write(fOut);
        } catch (Exception e) {
            logger.error("生成excel异常", e);
        }
        logger.debug("文件生成...");
    }

    Sheet buildFirstSheet(HSSFWorkbook workbook, String fileName, HSSFCellStyle cellStyle,
                          List<Staff> staffs) {
        HSSFSheet sheet = workbook.createSheet("工资表");
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short) 400);
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 9));
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCell cell00 = firstRow.createCell(0, CellType.STRING);
        cell00.setCellStyle(cellStyle);
        cell00.setCellValue(fileName);
        HSSFRow secondRow = sheet.createRow(1);
        HSSFCell cell10 = secondRow.createCell(0, CellType.STRING);
        cell10.setCellValue("序号");
        HSSFCell cell11 = secondRow.createCell(1, CellType.STRING);
        cell11.setCellValue("姓名");
        HSSFCell cell12 = secondRow.createCell(2, CellType.STRING);
        cell12.setCellValue("基本工资");
        HSSFCell cell13 = secondRow.createCell(3, CellType.STRING);
        cell13.setCellValue("劳务");
        HSSFCell cell14 = secondRow.createCell(4, CellType.STRING);
        cell14.setCellValue("奖金");
        HSSFCell cell15 = secondRow.createCell(5, CellType.STRING);
        cell15.setCellValue("餐补");
        HSSFCell cell16 = secondRow.createCell(6, CellType.STRING);
        cell16.setCellValue("其他");
        HSSFCell cell17 = secondRow.createCell(7, CellType.STRING);
        cell17.setCellValue("社保");
        HSSFCell cell18 = secondRow.createCell(8, CellType.STRING);
        cell18.setCellValue("公积金");
        HSSFCell cell19 = secondRow.createCell(9, CellType.STRING);
        cell19.setCellValue("金额");
        HSSFCellStyle hlinkStyle = workbook.createCellStyle();
        HSSFFont hlinkFont = workbook.createFont();
        hlinkFont.setUnderline(HSSFFont.U_SINGLE);
        hlinkFont.setColor(HSSFColor.BLUE.index);
        hlinkStyle.setFont(hlinkFont);
        Staff staff;
        for (int i = 0; i < staffs.size(); i++) {
            staff = staffs.get(i);
            HSSFRow row = sheet.createRow(i + 2);//创建一行
            HSSFCell cell0 = row.createCell(0);//创建一列
            cell0.setCellValue(i + 1);
            HSSFCell cell = row.createCell(1);//创建一列
            HSSFCreationHelper helper = workbook.getCreationHelper();
            Hyperlink link = helper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#" + staff.getName() + "!A1");
            cell.setCellType(CellType.STRING);
            cell.setCellValue(staff.getName());
            cell.setHyperlink(link);
            cell.setCellStyle(hlinkStyle);

            HSSFCellStyle contextstyle = workbook.createCellStyle();
            HSSFDataFormat df = workbook.createDataFormat(); // 此处设置数据格式
            contextstyle.setDataFormat(df.getBuiltinFormat("#,##0.00"));//保留两位小数点

            HSSFCell cell2 = row.createCell(2, CellType.NUMERIC);//基本工资
            cell2.setCellStyle(contextstyle);
            cell2.setCellValue(staff.getBasicWage().setScale(2).doubleValue());
            HSSFCell cell3 = row.createCell(3, CellType.NUMERIC);//劳务
            cell3.setCellValue(0);
            HSSFCell cell4 = row.createCell(4, CellType.NUMERIC);//奖金
            cell4.setCellValue(0);
            HSSFCell cell5 = row.createCell(5, CellType.NUMERIC);//餐补
            cell5.setCellValue(staff.getSubsidizedMeals().setScale(2).toString());
            HSSFCell cell6 = row.createCell(6, CellType.NUMERIC);//其他
            cell6.setCellValue(staff.getOther().setScale(2).toString());
            HSSFCell cell7 = row.createCell(7, CellType.NUMERIC);//社保
            cell7.setCellValue(staff.getSocialSecurity().setScale(2).toString());
            HSSFCell cell8 = row.createCell(8, CellType.NUMERIC);//公积金
            cell8.setCellValue(staff.getAccumulationFund().setScale(2).toString());
            HSSFCell cell9 = row.createCell(9, CellType.NUMERIC);//金额
            cell9.setCellValue(0);
        }
        HSSFRow row = sheet.createRow(staffs.size() + 2);//创建一行
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("合计");
        return sheet;
    }

    List<HSSFSheet> createStaffSheets(List<Staff> staffs, HSSFWorkbook workbook) {
        List<HSSFSheet> sheets = Lists.newArrayList();
        for (Staff staff : staffs) {
            sheets.add(workbook.createSheet(staff.getName()));
        }
        return sheets;
    }

}
