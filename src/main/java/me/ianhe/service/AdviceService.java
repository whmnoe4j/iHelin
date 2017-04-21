package me.ianhe.service;

import me.ianhe.dao.AdviceMapper;
import me.ianhe.db.entity.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdviceService {

    @Autowired
    private AdviceMapper adviceMapper;

    public int insertMessage(Advice advice) {
        return adviceMapper.insert(advice);
    }

    public List<Advice> selectAdviceByCondition() {
        return adviceMapper.selectAdviceByCondition();
    }

    /**
     * 事物测试
     */
    public void testTrans() {
        Advice advice = new Advice();
        advice.setEmail("email");
        advice.setCreateTime(new Date());
        advice.setMessage("msg");
        advice.setName("name");
        adviceMapper.insert(advice);
        throw new RuntimeException("事物");
    }

}
