package com.seven.ihelin.manager;

import com.seven.ihelin.db.entity.Advice;
import com.seven.ihelin.db.mapper.AdviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdviceManager {

    @Autowired
    private AdviceMapper adviceMapper;

    public int insertMessage(Advice advice) {
        return adviceMapper.insert(advice);
    }

    public List<Advice> selectAdviceByCondition() {
        return adviceMapper.selectAdviceByCondition();
    }

}
