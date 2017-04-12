package me.ianhe.service;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.Activity;
import me.ianhe.db.entity.Staff;
import me.ianhe.dao.ActivityMapper;
import me.ianhe.dao.StaffMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 财务管理
 *
 * @author iHelin
 * @create 2017-02-18 12:47
 */
@Service
public class FinanceService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private ActivityMapper activityMapper;

    public int addStaff(Staff staff) {
        return staffMapper.insert(staff);
    }

    public int removeStaff(Integer id) {
        return staffMapper.deleteByPrimaryKey(id);
    }

    public int updateStaff(Staff staff) {
        return staffMapper.updateByPrimaryKey(staff);
    }

    public Staff getStaffById(Integer id) {
        return staffMapper.selectByPrimaryKey(id);
    }

    public List<Staff> listStaffByCondition(int offset, int size) {
        Map<String, Object> param = Maps.newHashMap();
        return staffMapper.listByCondition(param, new RowBounds(offset, size));
    }

    public List<Staff> listStaffByCondition() {
        Map<String, Object> param = Maps.newHashMap();
        return staffMapper.listByCondition(param);
    }

    public int listStaffCount() {
        Map<String, Object> param = Maps.newHashMap();
        return staffMapper.listCount(param);
    }

    public int addActivity(Activity activity) {
        return activityMapper.insert(activity);
    }

    public int removeActivity(Integer id) {
        return activityMapper.deleteByPrimaryKey(id);
    }

    public int updateActivity(Activity staff) {
        return activityMapper.updateByPrimaryKey(staff);
    }

    public Activity getActivityById(Integer id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    public List<Activity> listActivityByCondition(Integer staffId, int offset, int size) {
        Map<String, Object> param = Maps.newHashMap();
        if (staffId != null) {
            param.put("staffId", staffId);
        }
        return activityMapper.listByCondition(param, new RowBounds(offset, size));
    }

    public int listActivityCount(Integer staffId) {
        Map<String, Object> param = Maps.newHashMap();
        if (staffId != null) {
            param.put("staffId", staffId);
        }
        return activityMapper.listCount(param);
    }

}
