package com.gy.cons;

public class Cons {
    private static ThreadLocal<String> CURRENT_ARAE = new ThreadLocal<>();

    public static void setCurrentArea(String area){
        CURRENT_ARAE.remove();
        CURRENT_ARAE.set(area);
    }

    public static String getCurrentArea(){
        String area = CURRENT_ARAE.get();
        return area;
    }



}
