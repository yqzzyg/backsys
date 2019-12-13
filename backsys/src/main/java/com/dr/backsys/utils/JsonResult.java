package com.dr.backsys.utils;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/13 9:15
 */
public class JsonResult {
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
