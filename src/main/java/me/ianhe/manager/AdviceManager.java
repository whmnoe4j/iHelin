package me.ianhe.manager;

import me.ianhe.db.entity.Advice;
import me.ianhe.db.mapper.AdviceMapper;
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
