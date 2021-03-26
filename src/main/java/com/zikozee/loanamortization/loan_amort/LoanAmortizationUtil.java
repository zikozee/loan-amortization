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
     * @param beginningBal beginning bal
     * @param interest period interest rate expressed as a decimal
     * @return Monthly interest
     */
    public static double interestPerPeriod(double beginningBal, double interest){
        return roundOff(beginningBal * (interest)/NUMBER_OF_MONTHS/PERCENTAGE);
    }

    public static double cumulativeInterest(double currentSummedInterest, double interest){
        return roundOff(currentSummedInterest + interest);
    }

    /**
     *
     * @param totalPayment  the calculated scheduled payment
     * @param monthlyInterest calculated monthly interest
     * @return principal
     */
    public static double calculatedPrincipal(double totalPayment, double monthlyInterest){
        return roundOff(totalPayment - monthlyInterest);
    }

    /**
     *
     * @param scheduledPayment
     * @param extraPayment
     * @param begBal
     * @param principal
     * @return
     */
    public static double calculatedBalance(double scheduledPayment, double extraPayment, double begBal, double principal){
        double calculatedBalance = scheduledPayment + extraPayment <= begBal
                ? begBal-principal : 0;
        return roundOff(calculatedBalance);
    }

    /**
     *
     * @param scheduledPayment
     * @param extraPayments
     * @param begBal
     * @return
     */
    public static double totalPayments(double scheduledPayment, double extraPayments, double begBal){
        double totalPayment = Math.min((scheduledPayment + extraPayments), begBal);

        return roundOff(totalPayment);
    }

    public static double computeExtraPayment(double scheduledPayment, double extraPayments, double begBal){
        double computedExtraPayment = (scheduledPayment + extraPayments) < begBal
                ? extraPayments
                : (begBal - scheduledPayment > 0) ? (begBal - scheduledPayment) : 0;

        return roundOff(computedExtraPayment);
    }

    //TODO DECIDE WHAT THIS WILL BE
    private static double roundOff(double input){
        return Math.round(input * 100.0) / 100.0;
    }
}
