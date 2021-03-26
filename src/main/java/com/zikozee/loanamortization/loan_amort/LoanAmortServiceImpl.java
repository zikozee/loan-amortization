package com.zikozee.loanamortization.loan_amort;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.zikozee.loanamortization.loan_amort.LoanAmortizationUtil.*;


@Service
public class LoanAmortServiceImpl implements LoanAmortService{

    @Override
    public List<LoanAmortResDTO> calculateLoanAmortization(@NotNull LoanAmortReqDTO loanAmortReqDTO) {
        List<LoanAmortResDTO> loanAmortResDTOList = new ArrayList<>();

        int numberOfTimes = loanAmortReqDTO.getNoOfPaymentPerYear() * loanAmortReqDTO.getLoanPeriodInYears();

        double scheduledPayment =
                LoanAmortizationUtil.calculateScheduledPayment(loanAmortReqDTO.getInterestRate(), loanAmortReqDTO.getLoanAmount(),
                        numberOfTimes);

        double interest = interestPerPeriod(loanAmortReqDTO.getLoanAmount(), loanAmortReqDTO.getInterestRate());
        double totalPayment =  totalPayments(scheduledPayment, loanAmortReqDTO.getExtraPayment(), loanAmortReqDTO.getLoanAmount());
        double principal = calculatedPrincipal(totalPayment, interest);
        double balance = calculatedBalance(scheduledPayment, loanAmortReqDTO.getExtraPayment(), loanAmortReqDTO.getLoanAmount(), principal);

        double extraPayments = computeExtraPayment(scheduledPayment, loanAmortReqDTO.getExtraPayment(), loanAmortReqDTO.getLoanAmount());

        LoanAmortResDTO amortResDTO = LoanAmortResDTO.builder().paymentNo(1).paymentDate(loanAmortReqDTO.getStartDate())
                .begBalance(loanAmortReqDTO.getLoanAmount())
                .scheduledPmt(scheduledPayment)
                .extraPmt(extraPayments)
                .totalPmt(totalPayment)
                .interest(interest).principal(principal).endingBal(balance).cumulativeInt(interest).build();

        loanAmortResDTOList.add(amortResDTO);

        int counter  = 1;
        double cumulativeInterest = interest;
        for (int i = 0; i < numberOfTimes - 1; i++) {
            counter++;

            interest = interestPerPeriod(amortResDTO.getEndingBal(), loanAmortReqDTO.getInterestRate());
            cumulativeInterest = cumulativeInterest(cumulativeInterest, interest);
            totalPayment = totalPayments(amortResDTO.getScheduledPmt(), loanAmortReqDTO.getExtraPayment(), amortResDTO.getBegBalance());
            principal = calculatedPrincipal(totalPayment, interest);
            balance = calculatedBalance(amortResDTO.getScheduledPmt(), amortResDTO.getExtraPmt(), amortResDTO.getEndingBal(), principal);
            LocalDate  newDate= amortResDTO.getPaymentDate().plusMonths(1);
            extraPayments = computeExtraPayment(amortResDTO.getScheduledPmt(), loanAmortReqDTO.getExtraPayment(), amortResDTO.getBegBalance());
            amortResDTO = LoanAmortResDTO.builder().paymentNo(counter).paymentDate(newDate).begBalance(amortResDTO.getEndingBal())
                    .scheduledPmt(scheduledPayment).extraPmt(extraPayments).totalPmt(totalPayment)
                    .interest(interest).principal(principal).endingBal(balance).cumulativeInt(cumulativeInterest).build();

            loanAmortResDTOList.add(amortResDTO);
        }


        return loanAmortResDTOList.stream()
                .sorted(Comparator.comparing(LoanAmortResDTO::getPaymentNo))
                .collect(Collectors.toList());
    }

}
