package com.dr.backsys.utils.dateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/13 12:01
 */
public class DateUtil {


    /**
     * 获取当前时间
     *   yyyyMMdd
     * @return
     */
    public static String dateFormat(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
