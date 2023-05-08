package com.movie.movieapi.util;


public class MaskingUtil {

    public static String maskName(String name) {
        StringBuilder maskedName = new StringBuilder(name);
        int length = name.length();
        if(length > 1) {
            for(int i = 1; i < Math.max(2, length - 1); i++) {
                maskedName.setCharAt(i, '*');
            }
        }
        return maskedName.toString();
    }
}
