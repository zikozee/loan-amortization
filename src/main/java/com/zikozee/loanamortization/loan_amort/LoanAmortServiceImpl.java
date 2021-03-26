package com.zikozee.loanamortization.loan_amort;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoanAmortServiceImpl implements LoanAmortService{

    @Override
    public List<LoanAmortResDTO> calculateLoanAmortization(@NotNull LoanAmortReqDTO loanAmortReqDTO) {
        List<LoanAmortResDTO> loanAmortResDTOList = new ArrayList<>();

        int numberOfTimes = loanAmortReqDTO.getNoOfPaymentPerYear() * loanAmortReqDTO.getLoanPeriodInYears();

        double scheduledPayment =
                LoanAmortizationUtil.calculateScheduledPayment(loanAmortReqDTO.getInterestRate(), loanAmortReqDTO.getLoanAmount(),
                        numberOfTimes);

        double interest = LoanAmortizationUtil.interestPerPeriod(loanAmortReqDTO.getLoanAmount(), loanAmortReqDTO.getInterestRate());
        double principal = LoanAmortizationUtil.calculatedPrincipal(scheduledPayment, interest);
        double balance = LoanAmortizationUtil.balance(loanAmortReqDTO.getLoanAmount(),principal);

        LoanAmortResDTO amortResDTO = LoanAmortResDTO.builder().paymentNo(1).scheduledPmt(scheduledPayment)
                .interest(interest).principal(principal).endingBal(balance).build();

        loanAmortResDTOList.add(amortResDTO);

        int counter  = 1;
        for (int i = 0; i < numberOfTimes - 1; i++) {
            counter++;

            interest = LoanAmortizationUtil.interestPerPeriod(amortResDTO.getEndingBal(), loanAmortReqDTO.getInterestRate());
            principal = LoanAmortizationUtil.calculatedPrincipal(amortResDTO.getScheduledPmt(), interest);
            balance = LoanAmortizationUtil.balance(amortResDTO.getEndingBal(), principal);

            amortResDTO = LoanAmortResDTO.builder().paymentNo(counter).scheduledPmt(scheduledPayment)
                    .interest(interest).principal(principal).endingBal(balance).build();

            loanAmortResDTOList.add(amortResDTO);
        }


        return loanAmortResDTOList.stream()
                .sorted(Comparator.comparing(LoanAmortResDTO::getPaymentNo))
                .collect(Collectors.toList());
    }

}
