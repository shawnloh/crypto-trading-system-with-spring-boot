package tech.betterwith.tradingsystem.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.betterwith.tradingsystem.dtos.GenericAPIResponseDto;
import tech.betterwith.tradingsystem.exceptions.OrderException;
import tech.betterwith.tradingsystem.exceptions.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> response.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericAPIResponseDto> myResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("Resource not found: {}", e.getMessage());
        return new ResponseEntity<>(new GenericAPIResponseDto(e.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericAPIResponseDto> myException(Exception e) {
        logger.error("Exception occurred: {}", e.getMessage());
        return new ResponseEntity<>(new GenericAPIResponseDto("Something went wrong", false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<GenericAPIResponseDto> myResourceNotFoundException(OrderException e) {
        logger.error("Order Exception: {}", e.getMessage());
        return new ResponseEntity<>(new GenericAPIResponseDto(e.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
