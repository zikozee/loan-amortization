package com.zikozee.loanamortization.loan_amort;

import com.zikozee.loanamortization.service.MapValidationErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(LoanAmortController.BASE_URL)
public class LoanAmortController {
    public static final String BASE_URL= "loanAmortization";

    private final LoanAmortService loanAmortService;
    private final MapValidationErrorService mapValidationErrorService;


    @PostMapping
    public ResponseEntity<?> calculateLoanAmortization(@RequestBody LoanAmortReqDTO loanAmortReqDTO, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        log.info("{}",loanAmortService.calculateLoanAmortization(loanAmortReqDTO));
        return ResponseEntity.ok(loanAmortService.calculateLoanAmortization(loanAmortReqDTO));
    }
}
