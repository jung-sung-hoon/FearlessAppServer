package com.fans.bravegirls.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils extends org.springframework.util.StringUtils {
    public static String getRandomStr(int size) {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            String randomStr = String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
            sb.append(randomStr);
        }
        return sb.toString();
    }

    public static String getRandomNum(int size) {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            String randomStr = String.valueOf((char) ((int) (rnd.nextInt(10)) + 48));
            sb.append(randomStr);
        }
        return sb.toString();
    }

    public static String numFormatter(int size, int value) {
        String suffix = String.format("%0"+size+"d", value);
        return suffix;
    }

    public static String numFormatter(int size, String value) {
        return numFormatter(size,Integer.parseInt(value));
    }

    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    //랜덤숫자를 가지고 온다
    public static int getRan(int min,int max) {
        int ran = ThreadLocalRandom.current().nextInt(min, max);
        return ran;
    }

    public static String lpad( String strContext, int iLen, String strChar ) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();
        for( int i = strContext.length(); i < iLen; i++ ) {
            // iLen길이 만큼 strChar문자로 채운다.
            sbAddChar.append( strChar );
        }
        strResult = sbAddChar + strContext;
        // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
        return strResult;
    }

}
