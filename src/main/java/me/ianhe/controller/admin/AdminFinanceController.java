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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
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

    @ResponseBody
    @RequestMapping(value = "staff", method = RequestMethod.POST)
    public String addStaff(Staff staff) {
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
        Calendar calendar = Calendar.getInstance();
        String fileName = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月工资表";
        OutputStream fOut = null;
        try {
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, CharEncoding.UTF_8) + ".xls");
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
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
        } catch (UnsupportedEncodingException e1) {
        } catch (Exception e) {
        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                }
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException e) {
            }
        }
        System.out.println("文件生成...");
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
        HSSFCell cell10 = secondRow.createCell(0,CellType.STRING);
        cell10.setCellValue("序号");
        HSSFCellStyle hlinkStyle = workbook.createCellStyle();
        HSSFFont hlinkFont = workbook.createFont();
        hlinkFont.setUnderline(HSSFFont.U_SINGLE);
        hlinkFont.setColor(HSSFColor.BLUE.index);
        hlinkStyle.setFont(hlinkFont);
        Staff staff;
        for (int i = 2; i <= staffs.size()+1; i++) {
            staff = staffs.get(i-1);
            HSSFRow row = sheet.createRow(i);//创建一行
            HSSFCell cell = row.createCell(0);//创建一列
            HSSFCreationHelper helper = workbook.getCreationHelper();
            Hyperlink link = helper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#"+staff.getName()+"!A1");
            cell.setCellType(CellType.STRING);
            cell.setCellValue(staff.getName());
            cell.setHyperlink(link);
            cell.setCellStyle(hlinkStyle);
//                cell.setCellFormula("HYPERLINK(\"[test.xls]'赖玉霞'!A1\",\"homepage\")");
        }
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
