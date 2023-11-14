package com.bargainbee.itemlistingservice.common.exceptionhandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest requestServlet = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = requestServlet.getRequestURI();

        //Creating the body of the response
        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                status.value(),
                status.toString(),
                "The request contains invalid data, probably left some field empty or null. " +
                        "Please check the request body and try again.",
                path
        );

        return ResponseEntity.badRequest().body(customErrorMessage);
    }


}
