package me.ianhe.aop;

import java.sql.SQLException;

/**
 * @author iHelin
 * @create 2017-04-15 21:18
 */
public class ViewSpaceService {

    public void deleteViewSpace(int spaceId) {
        throw new RuntimeException("运行异常。");
    }

    public void updateViewSpace() throws Exception {
        throw new SQLException("数据库更新操作异常。");
    }

}
