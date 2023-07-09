package com.deta.achi.pojo.handler;

import org.apache.ibatis.type.*;
import org.apache.poi.ss.formula.functions.T;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author by diaozhiwei on 2022/08/13.
 * @email diaozhiwei2k@163.com
 */
@MappedTypes({Date.class})
public class DateTypeHandler extends BaseTypeHandler<Date> {

    private static final SimpleDateFormat SDF_DATE_TIME_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        if (date != null) {
//            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            String format = SDF_DATE_TIME_ALL.format(date);
            preparedStatement.setString(i, format);
        }
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        try {
            String string = resultSet.getString(s);
            return (string != null && !string.equals("")) ? SDF_DATE_TIME_ALL.parse(string) : null;
        } catch (ParseException e) {
            return null;
        }

    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try {
            String string = resultSet.getString(i);
            return (string != null && !string.equals("")) ? SDF_DATE_TIME_ALL.parse(string) : null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            String string = callableStatement.getString(i);
            return (string != null && !string.equals("")) ? SDF_DATE_TIME_ALL.parse(string) : null;
        } catch (ParseException e) {
            return null;
        }
    }
}
