package me.ianhe.controller.admin;

import me.ianhe.db.entity.FiCustomer;
import me.ianhe.db.entity.FiPayment;
import me.ianhe.db.entity.FiSummary;
import me.ianhe.exception.SystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 应收账款
 *
 * @author iHelin
 * @since 2017/8/24 17:43
 */
@RestController
@RequestMapping("admin/fi")
public class AdminFinanceController extends BaseAdminController {

    /**
     * 添加客户
     *
     * @author iHelin
     * @since 2017/8/25 09:32
     */
    @PostMapping("customer")
    public String addCustomer(FiCustomer customer) {
        try {
            financeService.addCustomer(customer);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 分页获取客户
     *
     * @author iHelin
     * @since 2017/8/25 10:11
     */
    @GetMapping("customers")
    public String getCustomerPage(String customerName, Integer pageNum, Integer pageSize) {
        List<FiCustomer> customers = financeService.listCustomerByCondition(customerName,
                (pageNum - 1) * pageSize, pageSize);
        int total = financeService.listCustomerCount(customerName);
        return page(customers, total);
    }

    /**
     * 获取所有客户
     * 组件使用
     *
     * @author iHelin
     * @since 2017/8/25 10:11
     */
    @GetMapping("customers/component")
    public String getAllCustomer() {
        List<FiCustomer> customers = financeService.listCustomerByCondition(null);
        return success(customers);
    }

    /**
     * 分页获取应收
     *
     * @author iHelin
     * @since 2017/8/26 13:23
     */
    @GetMapping("summaries")
    public String getSummaryPage(Integer pageNum, Integer pageSize) {
        List<FiSummary> summaries = financeService.listSummaryByCondition((pageNum - 1) * pageSize, pageSize);
        int total = financeService.listSummaryCount();
        return page(summaries, total);
    }

    /**
     * 添加应收
     *
     * @author iHelin
     * @since 2017/8/26 14:13
     */
    @PostMapping("summary")
    public String addSummary(FiSummary fiSummary) {
        try {
            financeService.addSummary(fiSummary);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 收款
     *
     * @author iHelin
     * @since 2017/8/26 21:30
     */
    @PostMapping("payment")
    public String addPayment(FiPayment fiPayment) {
        try {
            financeService.addPayment(fiPayment);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    @GetMapping("aging/analysis")
    public String agingAnalyse(Date curDate, Integer pageNum, Integer pageSize) {
        return page(financeService.getIntervalSummary(curDate, (pageNum - 1) * pageSize, pageSize),
                financeService.getIntervalSummaryCount(curDate));
    }

    @GetMapping("analysis/detail")
    public String getAnalysisDetail(Integer customerID, Integer type) {
        return success(financeService.getSummaryByAcing(customerID, type, null));
    }

    @GetMapping("analysis/clear")
    public String getAlreadyClear(Integer customerID) {
        return success(financeService.getAlreadyClear(customerID));
    }

}
