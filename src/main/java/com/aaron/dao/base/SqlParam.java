package com.aaron.dao.base;

/**
 * Created by Administrator on 2016/6/8.
 */
public class SqlParam {
    public String paramName;
    public Object paramValue;

    public SqlParam(String name, Object value) {
        this.paramName = name;
        this.paramValue = value;
    }
}
