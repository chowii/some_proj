package com.soho.sohoapp.utils;

import com.soho.sohoapp.feature.home.portfolio.data.PropertyFinance;

public final class PropertyCalculator {

    private PropertyCalculator() {
        //utility class
    }

    public static double calculateYield(PropertyFinance finance) {
        double weeklyRent = finance.isRented() ? finance.getActualRent() : finance.getEstimatedRent();
        if (weeklyRent == 0 || finance.getPurchasePrice() == 0) {
            return 0;
        }

        return ((weeklyRent * 52) / finance.getPurchasePrice()) * 100;
    }

    public static double calculateLvr(PropertyFinance finance) {
        double loanAmount = finance.getLoanAmount();
        double price = finance.getPurchasePrice();

        if (loanAmount == 0 || price == 0) {
            return 0;
        }

        return (loanAmount / price) * 100;
    }

    public static double calculateValueChange(PropertyFinance finance) {
        return finance.getEstimatedValue() - finance.getPurchasePrice();
    }
}
