package com.unewej.mutual.util.http;

import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundException(ServerHttpRequest request, NotFoundException e) {
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, InvalidInputException e) {
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, request, e);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus status, ServerHttpRequest request, Exception e) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = e.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", status, path, message);
        return new HttpErrorInfo(path, status, message);
    }
}
