package me.ianhe.service;

import me.ianhe.dao.AdviceMapper;
import me.ianhe.db.entity.Advice;
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

    public int insertTest(Advice advice) {
        adviceMapper.insert(advice);
        throw new RuntimeException("一个异常");//这里抛出了一个异常，看是否会回滚
    }

}
