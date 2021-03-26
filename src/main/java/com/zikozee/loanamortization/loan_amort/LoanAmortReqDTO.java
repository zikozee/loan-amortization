package com.zikozee.loanamortization.loan_amort;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanAmortReqDTO {

    @NotNull(message = "Loan Amount is required")
    private double loanAmount;
    @NotNull(message = "interest is required")
    private double interestRate;
    private double extraPayment;
    @NotNull(message = "Loan Period is required")
    private int loanPeriodInYears;
    @NotNull(message = "Number of payment per year is required")
    private int noOfPaymentPerYear;
    @NotNull(message = "start Date is required")
    private LocalDate startDate;
}
