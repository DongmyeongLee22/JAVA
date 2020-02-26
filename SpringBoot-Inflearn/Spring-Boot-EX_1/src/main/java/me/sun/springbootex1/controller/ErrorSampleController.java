package me.sun.springbootex1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Controller
public class ErrorSampleController {
    /* BasicErrorController가 기본적인 에러 처리를 지원해준다. */

    @GetMapping("/error")
    public String hello() {
        throw new ErrorSampleException();
    }

    @ExceptionHandler(ErrorSampleException.class)
    public @ResponseBody
    AppError sampleError(ErrorSampleException e) {
        return new AppError("error.app.key", "IDK IDK IDK");
    }


}
