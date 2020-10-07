package com.gy.config;

public class DataSourceKey {
    public static final String XUZHOU = "XUZHOU";
    public static final String NANJING = "NANJING";
    public static final String BEIJING = "BEIJING";

    public static String getArea(Integer areaNum){
        switch (areaNum){
            case 1:
                return XUZHOU;
            case 2:
                return NANJING;
            case 3:
                return BEIJING;
        }
        return null;
    }
}
