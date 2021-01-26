package subway.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.core.AlreadyExistLineNameException;
import subway.core.AlreadyExistSectionException;
import subway.core.IllegalSectionException;

@RestControllerAdvice(basePackages = "subway.web")
public class SubwayAppExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
            AlreadyExistSectionException.class,
            IllegalSectionException.class,
            IllegalStateException.class,
            RuntimeException.class})
    public String handle500(RuntimeException e){
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            AlreadyExistLineNameException.class})
    public String handlerBadRequest(RuntimeException e){
        return e.getMessage();
    }



}
