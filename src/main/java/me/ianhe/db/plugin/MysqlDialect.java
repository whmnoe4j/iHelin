package me.ianhe.db.plugin;

import org.apache.commons.lang3.StringUtils;

public class MysqlDialect extends AbstractDialect {

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);
        if (StringUtils.isNotBlank(sql)) {
            pagingSelect.append(DEF_SQL_LIMIT);
            if (offset > 0) {
                pagingSelect.append(offsetPlaceholder).append(DEF_SQL_LIMIT_CONNECTOR).append(limitPlaceholder);
            } else {
                pagingSelect.append(limitPlaceholder);
            }
        }
        return pagingSelect.toString();
    }
}
