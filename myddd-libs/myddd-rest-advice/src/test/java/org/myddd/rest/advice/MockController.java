package org.myddd.rest.advice;

import org.myddd.lang.BadParameterException;
import org.myddd.lang.BusinessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class MockController {

    /**
     * 抛出一个业务异常，不带data
     */
    @GetMapping("/error/businessErrorOne")
    ResponseEntity<Object> businessException(){
        throw new BusinessException(MockErrorCode.MEDIA_NOT_FOUND);
    }

    /**
     * 抛出一个业务异常，带data,data可用于描述
     */
    @GetMapping("/error/businessErrorTwo")
    ResponseEntity<Object> businessTwoException(){
        throw new BusinessException(MockErrorCode.FILE_NOT_FOUND,"my_avatar.png");
    }

    /**
     * 错误参数异常，默认，errorCode为默认的BAD PARAMETER
     */
    @GetMapping("/error/badParameterException")
    ResponseEntity<Object> badParameterException(){
        throw new BadParameterException();
    }

    /**
     * 错误参数异常，指定自己的error code
     */
    @GetMapping("/error/badParameterExceptionTwo")
    ResponseEntity<Object> badParameterExceptionTwo(){
        throw new BadParameterException(MockErrorCode.BAD_PARAMETER);
    }

    /**
     * 错误参数异常，指定自己的error code,以及data
     */
    @GetMapping("/error/badParameterExceptionThree")
    ResponseEntity<Object> badParameterExceptionThree(){
        throw new BadParameterException(MockErrorCode.BAD_EMAIL,"ddd@ddd");
    }
}
