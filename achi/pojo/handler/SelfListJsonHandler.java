package com.deta.achi.pojo.handler;

import com.deta.common.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by diaozhiwei on 2022/08/13.
 * @email diaozhiwei2k@163.com
 */
@MappedJdbcTypes(JdbcType.LONGVARCHAR)
@MappedTypes({List.class})
public class SelfListJsonHandler extends BaseTypeHandler<List<Object>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Object> objects, JdbcType jdbcType) throws SQLException {
        if (objects != null) {
            String obj = JsonUtil.writeValueAsString(objects);
            preparedStatement.setObject(i, obj);
        }
    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String resultS = resultSet.getString(s);
        if (resultS != null && !resultS.equals("")) {
            return JsonUtil.parseObject(resultS, new TypeReference<>() {
            });
        }
        return new ArrayList<>();
    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String resultS = resultSet.getString(i);
        if (resultS != null && !resultS.equals("")) {
            return JsonUtil.parseObject(resultS, new TypeReference<>() {
            });
        }
        return new ArrayList<>();
    }

    @Override
    public List<Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String resultS = callableStatement.getString(i);
        if (resultS != null && !resultS.equals("")) {
            return JsonUtil.parseObject(resultS, new TypeReference<>() {
            });
        }
        return new ArrayList<>();
    }

}
