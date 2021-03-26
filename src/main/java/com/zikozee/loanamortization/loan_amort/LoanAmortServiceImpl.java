package com.zikozee.loanamortization.loan_amort;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.zikozee.loanamortization.loan_amort.LoanAmortizationUtil.totalPayments;


@Service
public class LoanAmortServiceImpl implements LoanAmortService{

    @Override
    public List<LoanAmortResDTO> calculateLoanAmortization(@NotNull LoanAmortReqDTO loanAmortReqDTO) {
        List<LoanAmortResDTO> loanAmortResDTOList = new ArrayList<>();

        int numberOfTimes = loanAmortReqDTO.getNoOfPaymentPerYear() * loanAmortReqDTO.getLoanPeriodInYears();

        double scheduledPayment =
                LoanAmortizationUtil.calculateScheduledPayment(loanAmortReqDTO.getInterestRate(), loanAmortReqDTO.getLoanAmount(),
                        numberOfTimes);

        double totalPayment =  totalPayments(scheduledPayment, loanAmortReqDTO.getExtraPayment(), loanAmortReqDTO.getLoanAmount());
        double interest = LoanAmortizationUtil.interestPerPeriod(loanAmortReqDTO.getLoanAmount(), loanAmortReqDTO.getInterestRate());
        double principal = LoanAmortizationUtil.calculatedPrincipal(scheduledPayment, interest);
        double balance = LoanAmortizationUtil.balance(loanAmortReqDTO.getLoanAmount(),principal);

        LoanAmortResDTO amortResDTO = LoanAmortResDTO.builder().paymentNo(1).paymentDate(loanAmortReqDTO.getStartDate())
                .begBalance(loanAmortReqDTO.getLoanAmount())
                .scheduledPmt(scheduledPayment)
                .extraPmt(loanAmortReqDTO.getExtraPayment())
                .totalPmt(totalPayment)
                .interest(interest).principal(principal).endingBal(balance).build();

        loanAmortResDTOList.add(amortResDTO);

        int counter  = 1;
        for (int i = 0; i < numberOfTimes - 1; i++) {
            counter++;

            totalPayment = totalPayments(amortResDTO.getScheduledPmt(), loanAmortReqDTO.getExtraPayment(), amortResDTO.getBegBalance());
            interest = LoanAmortizationUtil.interestPerPeriod(amortResDTO.getEndingBal(), loanAmortReqDTO.getInterestRate());
            principal = LoanAmortizationUtil.calculatedPrincipal(amortResDTO.getScheduledPmt(), interest);
            balance = LoanAmortizationUtil.balance(amortResDTO.getEndingBal(), principal);
            LocalDate  newDate= amortResDTO.getPaymentDate().plusMonths(1);
            amortResDTO = LoanAmortResDTO.builder().paymentNo(counter).paymentDate(newDate).begBalance(amortResDTO.getEndingBal())
                    .scheduledPmt(scheduledPayment).extraPmt(amortResDTO.getExtraPmt()).totalPmt(totalPayment)
                    .interest(interest).principal(principal).endingBal(balance).build();

            loanAmortResDTOList.add(amortResDTO);
        }


        return loanAmortResDTOList.stream()
                .sorted(Comparator.comparing(LoanAmortResDTO::getPaymentNo))
                .collect(Collectors.toList());
    }

}
