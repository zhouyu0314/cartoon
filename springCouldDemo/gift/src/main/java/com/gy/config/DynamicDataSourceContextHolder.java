package com.gy.config;

import java.util.ArrayList;
import java.util.List;


public class DynamicDataSourceContextHolder {

    //设置默认数据源，暂时不需要
    //private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.BEIJING::name);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    private static List<Object> dataSourceKeys = new ArrayList<>();

    public static void setDataSourceKey(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    public static List<Object> getDataSourceKeys() {
        return dataSourceKeys;
    }
}