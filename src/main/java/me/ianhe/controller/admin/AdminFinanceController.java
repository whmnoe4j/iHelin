package me.ianhe.controller.admin;

import me.ianhe.db.entity.Activity;
import me.ianhe.db.entity.Staff;
import me.ianhe.db.plugin.Pagination;
import me.ianhe.utils.DateTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
