package com.springboot.wzh.utils;

import org.springframework.util.DigestUtils;

import java.util.Calendar;

public class Common {
    public static String generateFileName(String token){
        String randomFilename = "";
        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        int seconds = calCurrent.get(Calendar.SECOND);
        int miSeconds = calCurrent.get(Calendar.MILLISECOND);
        randomFilename = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
                String.valueOf(intDay) + "_" +seconds+"_"+miSeconds+"_"+token.substring(token.length() - 6);
        return randomFilename;
    }
    public static String generateStreamSecret(String username){
        long time = System.currentTimeMillis();
        String origin = username + time;
        String md5Str = DigestUtils.md5DigestAsHex(origin.getBytes());
        return md5Str.substring(0,Math.min(7,md5Str.length()))+md5Str.substring(md5Str.length() - Math.min(md5Str.length(),7));
    }

}
