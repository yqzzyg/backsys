package com.neusoft.mid.ec.api.exception;

import javax.naming.NoPermissionException;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import me.puras.common.json.ReturnCode;

/**
 * Created by puras on 10/11/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @GetMapping("/error")
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Response<?> exception(Exception exception, WebRequest req) {
        logger.error("Exception {}", exception.getMessage(), exception);
        return ResponseHelper.createExceptionResponse(exception);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Response<?> exception(MethodArgumentNotValidException exception, WebRequest req) {
        logger.error("Exception {}", exception.getMessage(), exception);
        return ResponseHelper.createBindErrorResponse(exception.getMessage());
    }

    /*
     * @ExceptionHandler(value = CodeException.class)
     * @ResponseBody
     * @ResponseStatus(HttpStatus.OK) public Response<?> exception(CodeException exception,
     * WebRequest req) { logger.debug("CodeException code = {}, {}", exception.getCode(),
     * exception.getMessage()); return ResponseHelper.createResponse(exception.getCode(),
     * exception.getMessage()); }
     */

    @ExceptionHandler(value = { AuthorizationException.class, NoPermissionException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<?> exceptionForBidden(Exception exception, WebRequest req) {
        logger.error(exception.getClass().getSimpleName() + ":" + exception);
        // TODO 更新ReturnCode，增加无权限错误码
        return ResponseHelper.createResponse(-1, exception.getMessage());
    }

    /*
     * @ExceptionHandler(value = NotLoginException.class)
     * @ResponseBody
     * @ResponseStatus(HttpStatus.UNAUTHORIZED) public Response<?> exception(NotLoginException
     * exception, WebRequest req) { logger.debug("NotLoginException: ", exception); // TODO
     * 更新ReturnCode，增加无效Token的错误码 return ResponseHelper.createResponse(-2, exception.getMessage());
     * }
     */

    @ExceptionHandler(value = DataAccessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Response<?> exception(DataAccessException exception) {
        logger.error("DataAccessException: ", exception);
        return ResponseHelper.createResponse(ReturnCode.EXCEPTION,
                "数据库访问异常: " + exception.getClass().getSimpleName());
    }
}
