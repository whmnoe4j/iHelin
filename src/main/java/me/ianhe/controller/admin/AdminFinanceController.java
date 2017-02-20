package me.ianhe.controller.admin;

import me.ianhe.db.entity.Staff;
import me.ianhe.db.plugin.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping(value = "staff/{id}", method = RequestMethod.DELETE)
    public String deleteStaff(@PathVariable Integer id) {
        financeManager.removeStaff(id);
        return success();
    }

    @ResponseBody
    @RequestMapping(value = "staff/{id}", method = RequestMethod.GET)
    public String getStaffById(@PathVariable Integer id) {
        Staff staff = financeManager.getStaffById(id);
        return success(staff);
    }

}
