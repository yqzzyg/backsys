package com.dr.backsys.exception;

import com.dr.backsys.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionResolver {

    @ExceptionHandler(MyException.class)
    public JsonResult myException(MyException ex){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(ex.getCode());
        jsonResult.setMsg(ex.getMessage());
        return jsonResult;
    }


    @ExceptionHandler(Exception.class)
    public JsonResult commonException(Exception ex){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(500);
        jsonResult.setMsg(ex.getMessage());
        return jsonResult;
    }

}
