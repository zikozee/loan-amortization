package com.zikozee.loanamortization.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorService {

    ResponseEntity<?> MapValidationService(BindingResult result);
}