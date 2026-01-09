package util;

import java.sql.Types;

import org.hibernate.dialect.Dialect;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {

        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.VARCHAR, "text");
        registerColumnType(Types.BOOLEAN, "integer");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIMESTAMP, "datetime");
    }
}
