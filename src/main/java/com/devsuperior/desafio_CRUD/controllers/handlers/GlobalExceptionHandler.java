package com.devsuperior.desafio_CRUD.controllers.handlers;

import com.devsuperior.desafio_CRUD.dto.ErrorBeanValidationDTO;
import com.devsuperior.desafio_CRUD.dto.ErrorResponseDTO;
import com.devsuperior.desafio_CRUD.dto.FieldValidationError;
import com.devsuperior.desafio_CRUD.services.exceptions.ClientNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> ClientNotFound(ClientNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorBeanValidationDTO beanValidationDTO = new ErrorBeanValidationDTO(Instant.now(), status.value(), "Erro no processamento de dados.", request.getRequestURI());
        for (FieldError f : e.getFieldErrors()){
            beanValidationDTO.addErrors(new FieldValidationError(f.getField(), f.getDefaultMessage()));
        }
        return ResponseEntity.status(status).body(beanValidationDTO);
    }

}
