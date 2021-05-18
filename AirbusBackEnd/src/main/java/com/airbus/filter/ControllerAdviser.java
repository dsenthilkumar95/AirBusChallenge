package com.airbus.filter;

import com.airbus.exception.NoProductsForCategory;
import com.airbus.exception.ProductDTOValidationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoProductsForCategory.class)
    public ResponseEntity<Object> noProductsForCategoryHandler(NoProductsForCategory ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductDTOValidationFailure.class)
    public ResponseEntity<Object> ProductDTOValidationFailureHandler(ProductDTOValidationFailure ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("validationErrors", ex.getValidationErrors());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
