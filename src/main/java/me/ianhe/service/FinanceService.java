package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.dao.FiClearMapper;
import me.ianhe.dao.FiCustomerMapper;
import me.ianhe.dao.FiPaymentMapper;
import me.ianhe.dao.FiSummaryMapper;
import me.ianhe.db.entity.FiClear;
import me.ianhe.db.entity.FiCustomer;
import me.ianhe.db.entity.FiPayment;
import me.ianhe.db.entity.FiSummary;
import me.ianhe.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/8/24 18:03
 */
@Service
public class FinanceService {

    @Autowired
    private FiSummaryMapper fiSummaryMapper;

    @Autowired
    private FiClearMapper fiClearMapper;

    @Autowired
    private FiCustomerMapper fiCustomerMapper;

    @Autowired
    private FiPaymentMapper fiPaymentMapper;

    private static final String START_KEY = "start";
    private static final String END_KEY = "end";

    /**
     * 新增客户
     *
     * @author iHelin
     * @since 2017/8/24 20:01
     */
    public int addCustomer(FiCustomer fiCustomer) throws SystemException {
        if (StringUtils.isBlank(fiCustomer.getCustomerName())) {
            throw new SystemException("公司名称不能为空！");
        }
        if (fiCustomer.getCreditDays() == null || fiCustomer.getCreditDays() < 0) {
            throw new SystemException("信用期限不能为空且不能小于0");
        }
        int existCount = fiCustomerMapper.getCountByCustomerName(fiCustomer.getCustomerName());
        if (existCount > 0) {
            throw new SystemException("客户已存在");
        }
        return fiCustomerMapper.insert(fiCustomer);
    }

    /**
     * 新增应收
     *
     * @author iHelin
     * @since 2017/8/25 09:15
     */
    public int addSummary(FiSummary fiSummary) throws SystemException {
        if (fiSummary.getCustomerID() == null) {
            throw new SystemException("请选择客户");
        }
        if (fiSummary.getBaseDate() == null) {
            throw new SystemException("起算日不能为空");
        }
        if (fiSummary.getDebitAmount() == null || fiSummary.getDebitAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new SystemException("贷方金额不能小于或等于零");
        }
        FiCustomer customer = fiCustomerMapper.selectByPrimaryKey(fiSummary.getCustomerID());
        fiSummary.setCreditAmount(BigDecimal.ZERO);
        fiSummary.setBalanceAmount(fiSummary.getDebitAmount());
        fiSummary.setAccPeriod(customer.getCreditDays());
        fiSummary.setCreditFlag(false);
        fiSummary.setClearFlag(true);
        fiSummary.setDueDate(DateUtils.addDays(fiSummary.getBaseDate(), customer.getCreditDays()));
        return fiSummaryMapper.insert(fiSummary);
    }

    /**
     * 收款
     *
     * @author iHelin
     * @since 2017/8/26 21:29
     */
    public int addPayment(FiPayment fiPayment) {
        if (fiPayment.getCustomerID() == null) {
            throw new SystemException("请选择客户");
        }
        if (fiPayment.getReceiptDate() == null) {
            throw new SystemException("收款日期不能为空");
        }
        if (fiPayment.getPaymentAmount() == null || fiPayment.getPaymentAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new SystemException("贷方金额不能小于或等于零");
        }
        int res = fiPaymentMapper.insert(fiPayment);
        List<FiSummary> summaries = fiSummaryMapper.getSummaryByCustomerID(fiPayment.getCustomerID());
        BigDecimal remainAmount = fiPayment.getPaymentAmount();
        FiClear fiClear;
        for (FiSummary summary : summaries) {
            if (Boolean.TRUE.equals(summary.getClearFlag())) {
                if (remainAmount.compareTo(BigDecimal.ZERO) > 0) {
                    fiClear = new FiClear();
                    fiClear.setSummaryId(summary.getId());
                    fiClear.setPaymentID(fiPayment.getId());
                    fiClear.setCompanyId(fiPayment.getCustomerID());
                    fiClear.setBeforeBalance(summary.getBalanceAmount());
                    fiClear.setRemark(fiPayment.getRemark());
                    BigDecimal actualSetterAmt;
                    if (remainAmount.compareTo(summary.getBalanceAmount()) >= 0) {
                        remainAmount = remainAmount.subtract(summary.getBalanceAmount());
                        summary.setBalanceAmount(BigDecimal.ZERO);
                        summary.setCreditAmount(summary.getDebitAmount());
                        fiClear.setAfterBalance(BigDecimal.ZERO);
                        actualSetterAmt = summary.getBalanceAmount();
                    } else {
                        BigDecimal afterSummaryAmt = summary.getBalanceAmount().subtract(remainAmount);
                        summary.setBalanceAmount(afterSummaryAmt);
                        summary.setCreditAmount(summary.getCreditAmount().add(remainAmount));
                        fiClear.setAfterBalance(afterSummaryAmt);
                        actualSetterAmt = remainAmount;
                        remainAmount = BigDecimal.ZERO;
                    }
                    fiClear.setActualAmt(actualSetterAmt);
                    fiClearMapper.insert(fiClear);
                    fiSummaryMapper.updateByPrimaryKeySelective(summary);
                } else {
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 公司查询
     *
     * @author iHelin
     * @since 2017/8/25 09:16
     */
    public List<FiCustomer> listCustomerByCondition(String customerName) {
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(customerName)) {
            params.put("customerName", customerName);
        }
        return fiCustomerMapper.listByCondition(params);
    }

    /**
     * 客户查询 分页
     *
     * @author iHelin
     * @since 2017/8/25 09:16
     */
    public List<FiCustomer> listCustomerByCondition(String customerName, int offset, int size) {
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(customerName)) {
            params.put("customerName", customerName);
        }
        return fiCustomerMapper.listByCondition(params, new RowBounds(offset, size));
    }

    /**
     * 查询公司数量
     *
     * @author iHelin
     * @since 2017/8/25 09:16
     */
    public int listCustomerCount(String customerName) {
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(customerName)) {
            params.put("customerName", customerName);
        }
        return fiCustomerMapper.listCount(params);
    }

    /**
     * 应收查询 分页
     *
     * @author iHelin
     * @since 2017/8/25 09:17
     */
    public List<FiSummary> listSummaryByCondition(int offset, int size) {
        Map<String, Object> res = Maps.newHashMap();
        return fiSummaryMapper.listByCondition(res, new RowBounds(offset, size));
    }

    /**
     * 查询应收数量
     *
     * @author iHelin
     * @since 2017/8/25 09:17
     */
    public int listSummaryCount() {
        Map<String, Object> res = Maps.newHashMap();
        return fiSummaryMapper.listCount(res);
    }

    public List<HashMap> getIntervalSummary(Date curDate, int offset, int size) {
        return fiSummaryMapper.getIntervalSummary(curDate, new RowBounds(offset, size));
    }

    public int getIntervalSummaryCount(Date curDate) {
        return fiSummaryMapper.getIntervalSummaryCount(curDate);
    }

    /**
     * 查询冲销数量
     *
     * @author iHelin
     * @since 2017/8/25 09:17
     */
    public int listClearCount() {
        Map<String, Object> res = Maps.newHashMap();
        return fiClearMapper.listCount(res);
    }

    public List<HashMap> getSummaryByAcing(Integer customerID, Integer type, Date date) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("customerID", customerID);
        params.put("date", date);
        if (type != null) {
            switch (type) {
                case 15:
                    params.put(START_KEY, 0);
                    params.put(END_KEY, 15);
                    break;
                case 30:
                    params.put(START_KEY, 16);
                    params.put(END_KEY, 30);
                    break;
                case 60:
                    params.put(START_KEY, 31);
                    params.put(END_KEY, 60);
                    break;
                case 90:
                    params.put(START_KEY, 61);
                    params.put(END_KEY, 90);
                    break;
                case 120:
                    params.put(START_KEY, 91);
                    params.put(END_KEY, 120);
                    break;
                default:
                    break;
            }
        }
        return fiSummaryMapper.getSummaryByAcing(params);
    }

    public List<HashMap> getAlreadyClear(Integer customerID) {
        return fiSummaryMapper.getAlreadyClear(customerID);
    }
}
