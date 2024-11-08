package tech.betterwith.tradingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.betterwith.tradingsystem.dtos.GenericAPIResponseDto;
import tech.betterwith.tradingsystem.exceptions.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> response.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericAPIResponseDto> myResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new GenericAPIResponseDto(e.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericAPIResponseDto> myException(Exception e) {
        return new ResponseEntity<>(new GenericAPIResponseDto("Somethin went wrong", false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
