package com.zikozee.loanamortization.loan_amort;

public class LoanAmortizationUtil {
    public static final int NUMBER_OF_MONTHS = 12;
    public static final int PERCENTAGE = 100;

    private LoanAmortizationUtil(){}

    /**
     *
     * @param interest period interest rate expressed as a decimal
     * @param presentLoanValue present value of loan (loan amount)
     * @param numOfLoanPayment number of loan payments
     * @return monthly scheduled payment
     */
    public static double calculateScheduledPayment(double interest, double presentLoanValue, int numOfLoanPayment){
        double numerator = (interest/NUMBER_OF_MONTHS/PERCENTAGE) * presentLoanValue;
        double denominator = (1 - Math.pow((1 + (interest/NUMBER_OF_MONTHS/PERCENTAGE)), -numOfLoanPayment));

        return roundOff(numerator/denominator);
    }

    /**
     *
     * @param principal principal remaining
     * @param interest period interest rate expressed as a decimal
     * @return Monthly interest
     */
    public static double interestPerPeriod(double principal, double interest){
        return roundOff(principal * (interest)/NUMBER_OF_MONTHS/PERCENTAGE);
    }

    /**
     *
     * @param scheduledPayment  the calculated scheduled payment
     * @param monthlyInterest calculated monthly interest
     * @return principal
     */
    public static double calculatedPrincipal(double scheduledPayment, double monthlyInterest){
        return roundOff(scheduledPayment - monthlyInterest);
    }

    /**
     *
     * @param remainingLoanAmount amount left to pay
     * @param calculatedPrincipal calculated principal
     * @return remaining payable balance
     */
    public static double balance(double remainingLoanAmount, double calculatedPrincipal){
        return roundOff(remainingLoanAmount - calculatedPrincipal);
    }

    public static double totalPayments(double scheduledPayment, double extraPayments, double begBal){
        double totalPayment = Math.min((scheduledPayment + extraPayments), begBal);

        return roundOff(totalPayment);
    }

    //TODO DECIDE WHAT THIS WILL BE
    private static double roundOff(double input){
        return Math.round(input * 100.0) / 100.0;
    }
}
