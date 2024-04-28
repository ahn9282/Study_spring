package hello.exception.exhandler.advice;

import hello.exception.api.ApiExceptionV2Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@ControllerAdvice(annotations = RestController.class) //어노테이션 지정 어드바이스
//@ControllerAdvice("org.example.controllers") //패키지 포함 하위 컨트롤러 지정
//@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
// 특정 컨트롤러 클래스 지정 어드바이스 방식
//@RestControllerAdvice //RestController 클래스 지정 어드바이스
@RestControllerAdvice(basePackages = "hello.exception.api")
@Slf4j
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<ErrorResult>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
