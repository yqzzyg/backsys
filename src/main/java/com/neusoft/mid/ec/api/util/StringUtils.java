package com.neusoft.mid.ec.api.util;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by puras on 10/12/16.
 */
public class StringUtils {
    public static String randomCode(int size) {
        Random random = new Random(new Date().getTime());
        String code = String.valueOf(random.nextInt());
        return code.substring(code.length() - size);
    }


    /**
     * 清除html标签
     * @param htmlStr
     * @return
     */
    public static String removeHtmlTag(String htmlStr) {

        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
        //定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";
        //定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        String regEx_special = "\\&[a-zA-Z]{1,10};";

        //1.过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        //2.过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        //3.过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        //4.过滤特殊标签
        Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher m_special = p_special.matcher(htmlStr);
        htmlStr = m_special.replaceAll("");

        return htmlStr;
    }
}
