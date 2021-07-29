package com.t2012e.reflection;

import com.t2012e.reflection.myannotation.Id;
import com.t2012e.reflection.myannotation.Table;
import com.t2012e.util.ConnectionHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoReflection {
    public static void main(String[] args) throws SQLException {
        migrateData(Employee.class);
        migrateData(Customer.class);
    }

    private static void migrateData(Class clazz) throws SQLException {
        String tableName = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(Table.class)) { // nếu có annotation @Table thì lấy tên ra.
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table.name() != null && !table.name().isEmpty()) {
                tableName = table.name();
            }
        }
        StringBuilder sqlQueryBuilder = new StringBuilder();
        sqlQueryBuilder.append("CREATE TABLE");
        sqlQueryBuilder.append(" ");
        sqlQueryBuilder.append(tableName);
        sqlQueryBuilder.append("(");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            sqlQueryBuilder.append(field.getName());
            sqlQueryBuilder.append(" ");
            boolean isPrimaryKey = field.isAnnotationPresent(Id.class);
            if (field.getType().getSimpleName().equals("int")) {
                sqlQueryBuilder.append("INT ");
            } else if (field.getType().getSimpleName().equals("String")) {
                sqlQueryBuilder.append("VARCHAR(250) ");
            } else if (field.getType().getSimpleName().equals("double")) {
                sqlQueryBuilder.append("DOUBLE ");
            }
            if (isPrimaryKey) {
                Id id = field.getAnnotation(Id.class);
                if (id.autoIncrement()) {
                    sqlQueryBuilder.append("AUTO_INCREMENT ");
                }
                sqlQueryBuilder.append("PRIMARY KEY");
            }
            sqlQueryBuilder.append(", ");
        }
        sqlQueryBuilder.setLength(sqlQueryBuilder.length() - 2);
        sqlQueryBuilder.append(")");
        System.out.println(sqlQueryBuilder.toString());

        Connection cnn = ConnectionHelper.getConnection();
        Statement stt = cnn.createStatement();
        stt.execute(sqlQueryBuilder.toString());
    }

    public static void save(Object obj) throws SQLException, IllegalAccessException {
        Class clazz = obj.getClass();
//Kết nối database
        Connection cnn = ConnectionHelper.getConnection();
        Statement stt = cnn.createStatement();
        String tableName = clazz.getSimpleName();
        StringBuilder fieldNamesBuilder = new StringBuilder();
        fieldNamesBuilder.append("(");
        Field[] fields = clazz.getDeclaredFields();
        for (Field value : fields) {
            fieldNamesBuilder.append(value.getName());
            fieldNamesBuilder.append(", ");
        }
        fieldNamesBuilder.setLength(fieldNamesBuilder.length() - 2);
        fieldNamesBuilder.append(")");
        StringBuilder fieldValuesBuilder = new StringBuilder();
        fieldValuesBuilder.append("(");
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().getSimpleName().equals("String")) {
                fieldValuesBuilder.append("'");
                fieldValuesBuilder.append(field.get(obj));
                fieldValuesBuilder.append("', ");
            } else {
                fieldValuesBuilder.append(field.get(obj));
                fieldValuesBuilder.append(", ");
            }
        }
        fieldValuesBuilder.setLength(fieldValuesBuilder.length() - 2);
        fieldValuesBuilder.append(")");
//Tạo ra câu lệnh truy vấn.
        String sqlQuery = String.format("insert into %s " +
                "%s " +
                "values %s", tableName, fieldNamesBuilder.toString(), fieldValuesBuilder.toString());
        System.out.println(sqlQuery);
//Gửi vào database.
        stt.execute(sqlQuery);
    }
}
