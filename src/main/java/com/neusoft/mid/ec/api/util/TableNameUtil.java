package com.neusoft.mid.ec.api.util;

import java.util.Date;

public class TableNameUtil {

    /**
     *  获取当前月份表名
     *   指定表名为 user_action_log
     * @return
     */
    public static String getTableName(){
        try {
            String curruntDate = Date2TampsUtil.dateFormat("yyyyMM");
            return "user_action_log"+"_"+curruntDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *  获取当前月份表名
     *   传入需要分月的表名
     * @return
     */
    public static String getTableName(String fromtTable){
        try {
            String curruntDate = Date2TampsUtil.dateFormat("yyyyMM");
            return fromtTable+"_"+curruntDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *  获取指定月份表名
     *   传入需要分月的表名
     * @return
     */
    public static String getTableName(String fromtTable,Date date){
        try {
            String curruntDate = Date2TampsUtil.dateFormat(date,"yyyyMM");
            return fromtTable+"_"+curruntDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
