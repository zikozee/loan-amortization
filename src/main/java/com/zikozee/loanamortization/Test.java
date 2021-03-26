package com.zikozee.loanamortization;

import com.zikozee.loanamortization.loan_amort.LoanAmortizationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info("power things {}", Math.pow(5, 5.2));

        double pmt = LoanAmortizationUtil.calculateScheduledPayment((0.06/12), 100000, 36);

        log.info("scheduled payment  {}", pmt);
    }
}
