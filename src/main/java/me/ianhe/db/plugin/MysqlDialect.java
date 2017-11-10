package me.ianhe.db.plugin;

import org.apache.commons.lang3.StringUtils;

/**
 * Mysql Dialect
 *
 * @author iHelin
 * @since 2017/11/10 15:01
 */
public class MysqlDialect {

    private static final String DEF_SQL_LIMIT = " limit ";

    private static final String DEF_SQL_LIMIT_CONNECTOR = ",";

    public boolean supportsLimitOffset() {
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    /**
     * getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit))
     *
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);
        if (StringUtils.isNotBlank(sql)) {
            pagingSelect.append(DEF_SQL_LIMIT);
            if (offset > 0) {
                pagingSelect.append(offset).append(DEF_SQL_LIMIT_CONNECTOR).append(limit);
            } else {
                pagingSelect.append(limit);
            }
        }
        return pagingSelect.toString();
    }
}
