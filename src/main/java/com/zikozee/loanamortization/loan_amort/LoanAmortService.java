package com.zikozee.loanamortization.loan_amort;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LoanAmortService {

    List<LoanAmortResDTO> calculateLoanAmortization(@NotNull LoanAmortReqDTO loanAmortReqDTO);
}
