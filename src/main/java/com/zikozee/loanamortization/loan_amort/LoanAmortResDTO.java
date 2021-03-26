package com.zikozee.loanamortization.loan_amort;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanAmortResDTO {
    private int paymentNo;
    private LocalDate paymentDate;
    private double begBalance;
    private double scheduledPmt;
    private double extraPmt;
    private double totalPmt;
    private double principal;
    private double interest;
    private double endingBal;
    private double cumulativeInt;
}
