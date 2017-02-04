package me.ianhe.db.plugin;

import org.apache.commons.lang3.StringUtils;

public class MySQLDialect extends Dialect {

    public boolean supportsLimitOffset() {
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        String limitStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(sql)) {
            limitStr = sql + DEF_SQL_LIMIT;
            if (offset > 0) {
                limitStr += offsetPlaceholder + DEF_SQL_LIMIT_CONNECTOR + limitPlaceholder;
            } else {
                limitStr += limitPlaceholder;
            }
        }
        return limitStr;
    }
}
