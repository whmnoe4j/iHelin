package me.ianhe.service;

import me.ianhe.db.entity.Advice;
import me.ianhe.dao.AdviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
