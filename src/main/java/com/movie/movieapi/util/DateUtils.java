package com.movie.movieapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class DateUtils {
    public static String convertMongoDate(String val){
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        try {
            return outputFormat.format(inputFormat.parse(val));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
