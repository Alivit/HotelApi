package com.alivit.hotelservice.handler;

import com.alivit.hotelservice.dto.ExceptionResponse;
import com.alivit.hotelservice.handler.exception.ResourceNotCreatedException;
import com.alivit.hotelservice.handler.exception.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                notFound,
                webRequest.getDescription(false),
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(exceptionResponse, notFound);
    }

    @ExceptionHandler(value = {BadRequestException.class, ResourceNotCreatedException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest webRequest) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                badRequest,
                webRequest.getDescription(false),
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(exceptionResponse, badRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest webRequest) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                internalServerError,
                webRequest.getDescription(false),
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(exceptionResponse, internalServerError);
    }
}
