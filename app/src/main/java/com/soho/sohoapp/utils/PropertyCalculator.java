package com.soho.sohoapp.utils;

import com.soho.sohoapp.data.models.PropertyFinance;

public final class PropertyCalculator {

    private PropertyCalculator() {
        //utility class
    }

    public static double calculateYield(PropertyFinance finance) {
        double weeklyRent = finance.isRented() ? finance.getActualRent() : finance.getEstimatedRent();
        if (weeklyRent == 0 || finance.getEstimatedValue() == 0) {
            return 0;
        }

        return ((weeklyRent * 52) / finance.getEstimatedValue()) * 100;
    }

    public static double calculateLvr(PropertyFinance finance) {
        double loanAmount = finance.getLoanAmount();
        double estimatedValue = finance.getEstimatedValue();

        if (loanAmount == 0 || estimatedValue == 0) {
            return 0;
        }

        return (loanAmount / estimatedValue) * 100;
    }

    public static double calculateValueChange(PropertyFinance finance) {
        return finance.getEstimatedValue() - finance.getPurchasePrice();
    }
}
