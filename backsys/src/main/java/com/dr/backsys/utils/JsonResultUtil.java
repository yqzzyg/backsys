package com.dr.backsys.utils;

/**
 * @ Description   :
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/13 9:16
 */
public class JsonResultUtil {
    public static JsonResult setOK(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("ok");
        jsonResult.setData(null);
        return jsonResult;
    }
    public static JsonResult setOK(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("ok");
        jsonResult.setData(data);
        return jsonResult;
    }
    public static JsonResult setERROR(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("ERROR");
        jsonResult.setData(null);
        return jsonResult;
    }
    public static JsonResult setERROR(String msg){
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(1);
        jsonResult.setMsg(msg);
        jsonResult.setData(null);
        return jsonResult;
    }
    public static JsonResult setJsonResult(boolean issuccess){
        if (issuccess){
            return setOK();
        }else {
            return setERROR();
        }
    }
}
