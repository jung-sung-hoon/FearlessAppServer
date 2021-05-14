package com.fans.bravegirls.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.exception.ExceptionBase;
import com.fans.bravegirls.common.exception.http.ApiExecutionException;
import com.fans.bravegirls.common.exception.http.BadRequestException;
import com.fans.bravegirls.common.exception.http.UnauthorizedReqeustException;
import com.fans.bravegirls.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class BaseRestController {

    @ExceptionHandler(ExceptionBase.class)
    public ResponseEntity<?> handleBaseException(HttpServletRequest req, Exception ex) {
        log.error(ex.getLocalizedMessage());
        if(ex instanceof BadRequestException) {
            String errorMsg = getMsg(req, ex);
            return badReq(errorMsg);
        } else if(ex instanceof UnauthorizedReqeustException) {
            String errorMsg = getMsg(req, ex);
            return auth(errorMsg);
        } else if(ex instanceof ApiExecutionException) {
            String errorMsg = getMsg(req, ex);
            return auth(errorMsg);
        } else {
            String errorMsg = getMsg(req, ex);
            return forbiden(errorMsg);
        }
    }


    private String getMsg(HttpServletRequest req, Exception ex) {
        if(ex instanceof ExceptionBase) {
            ExceptionBase e = (ExceptionBase) ex;
            String msg = e.getMessage();
            String[] args = e.getArgs();
            try {
                if(args != null) {
                    return msg;
                }
                return msg;
            } catch(Exception e1) {
                return e1.getLocalizedMessage();
            }
        }
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest req, Exception ex) {
        String msg = ex.getMessage();
        log.warn(msg);

        return badReq("error.bad.request");
    }


    @ExceptionHandler(ApiExecutionException.class)
    public ResponseEntity<?> handleApiException(HttpServletRequest req, Exception ex) {
        String msg = ex.getMessage();
        log.warn(msg);

        return badReq(msg);
    }


    /**
     * 성공
     *
     * @param data
     * @return
     */
    protected ResponseEntity<?> success(Object data) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("200");
        restResult.setMessage("");
        restResult.setData(data);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    /**
     * 성공
     *
     * @param data
     * @return
     */
    protected ResponseEntity<?> success(Object data, String offset, String size) {
        return success(data, offset, size, 0);
    }


    protected ResponseEntity<?> success(Object data, String offset, String size, int total) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("200");
        restResult.setMessage("");
        restResult.setData(data);
        restResult.setOffset(offset);
        restResult.setSize(size);
        restResult.setTotal(String.valueOf(total));
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    /**
     * Fail
     *
     * @return
     */
    protected ResponseEntity<?> fail(String failMsg) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("400");
        restResult.setMessage(failMsg);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    /**
     * Fail
     *
     * @return
     */
    protected ResponseEntity<?> badReq(String failMsg) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("400");
        restResult.setMessage(failMsg);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    /**
     * Fail
     *
     * @return
     */
    protected ResponseEntity<?> auth(String failMsg) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("401");
        restResult.setMessage(failMsg);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    /**
     * Fail
     *
     * @return
     */
    protected ResponseEntity<?> forbiden(String failMsg) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("403");
        restResult.setMessage(failMsg);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(MethodArgumentNotValidException ex) {
        return badReq(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    protected void ipCheck(HttpServletRequest request) throws BadRequestException {
        boolean isCheck = true;
        if(!isCheck) {
            throw new BadRequestException("invalide ip");
        }
    }

}
