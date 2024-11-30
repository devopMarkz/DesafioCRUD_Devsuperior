package com.devsuperior.desafio_CRUD.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorBeanValidationDTO extends ErrorResponseDTO{

    private List<FieldValidationError> errors = new ArrayList<>();

    public ErrorBeanValidationDTO(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldValidationError> getErrors() {
        return errors;
    }

    public void addErrors(FieldValidationError error){
        errors.add(error);
    }
}
