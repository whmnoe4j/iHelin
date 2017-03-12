package me.ianhe.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class DataSourceFactoryBean implements FactoryBean<DruidDataSource>, DisposableBean {

    private static final int DBCP_INITIAL_SIZE = 5;
    private static final int DBCP_MAX_ACTIVE = 30;
    private static final int DBCP_MIN_IDLE = 5;
    private static final int DBCP_MAX_IDLE = 10;
    private static final int DBCP_MAX_WAIT = 20000;
    private static final int DBCP_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 300000;
    private static final int DBCP_MIN_EVICTABLE_IDLE_TIME_MILLIS = 320000;
    private static final int DBCP_REMOVE_ABANDONED_TIMEOUT = 150;

    private DruidDataSource dataSource;

    @Override
    public DruidDataSource getObject() throws Exception {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(CommonConfig.getDBDriver());
        String cfgUrl = CommonConfig.getDBUrl();
        if (cfgUrl != null && cfgUrl.contains("?"))
            cfgUrl += "&allowMultiQueries=true";
        else
            cfgUrl += "?allowMultiQueries=true";
        dataSource.setUrl(cfgUrl);
        dataSource.setUsername(CommonConfig.getDBUser());
        dataSource.setPassword(CommonConfig.getDBPwd());

        dataSource.setInitialSize(DBCP_INITIAL_SIZE);
        dataSource.setMaxActive(DBCP_MAX_ACTIVE);
        dataSource.setMinIdle(DBCP_MIN_IDLE);
        dataSource.setMaxIdle(DBCP_MAX_IDLE);
        dataSource.setMaxWait(DBCP_MAX_WAIT);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(DBCP_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        dataSource.setMinEvictableIdleTimeMillis(DBCP_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(DBCP_REMOVE_ABANDONED_TIMEOUT);
        dataSource.setLogAbandoned(true);
        return dataSource;
    }

    @Override
    public Class<DruidDataSource> getObjectType() {
        return DruidDataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}