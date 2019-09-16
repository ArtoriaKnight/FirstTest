package com.tutu2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class   StringUtil {

    private static Pattern p = Pattern.compile("\\d");
    private static Pattern REGEX_CHINESE = Pattern.compile("[\u4e00-\u9fa5]");
    private static Pattern REGEX_AZ = Pattern.compile("[a-zA-Z]");
    private static Pattern REGEX_TS = Pattern.compile("[_`~@#$%^&*()+=|{}:;\\\\[\\\\]<>~@#￥%……&*（）——+|{}【】‘；：”“。、]|\\n|\\r|\\t");
    private StringUtil() {
    }

     public static boolean isEmpty(String value) {
        int strLen;
        if(value != null && (strLen = value.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if(values != null && values.length != 0) {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }
        return result;
    }

    public static String getUnixTimestamp() {
            return String.valueOf(System.currentTimeMillis()/1000);
        }

    /**
     * 判断字符串是否包含数字
     * @param company
     * @return
     */
    public static boolean isContainNumber(String company) {
        Matcher m = p.matcher(company);
        return m.find();
    }
  /**
     * 判断字符串是否包含中文并替换
     * @param company
     * @return
     */
    public static String replaceCh(String company) {
        Matcher m = REGEX_CHINESE.matcher(company);
         if(m.find()){
             company =company.replaceAll("[\u4e00-\u9fa5]","");
         }
         return company;
    }

    public static String replaceNumber(String query) {
        Matcher m = p.matcher(query);
       if (m.find()){
           query = query.replaceAll("\\d","");
       }
       return query;
    }

    public static boolean isContainCh(String imageFileName) {
        Matcher m = REGEX_CHINESE.matcher(imageFileName);
        return m.find();
    }

    public static boolean isContainAz(String str) {
        Matcher m = REGEX_AZ.matcher(str);
        return m.find();
    }

    public static boolean isContainTs(String str) {
        Matcher m = REGEX_TS.matcher(str);
        return m.find();
    }

    /**
     * 替换所有可能字符,仅仅保留英文字母
     * @param str
     * @return
     */
    public static String replaceAllSign(String str){
        return str.replaceAll("[^a-z-A-Z]", "").toLowerCase();
    }
    
    public static void main(String[] args) {
		System.out.println(replaceAllSign("hello/what!"));
	}
}