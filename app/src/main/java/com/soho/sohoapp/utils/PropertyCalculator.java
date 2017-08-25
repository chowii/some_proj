package com.soho.sohoapp.utils;

import com.soho.sohoapp.home.portfolio.data.PortfolioFinance;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;

public final class PropertyCalculator {

    private PropertyCalculator() {
        //utility class
    }

    public static double calculateYield(PortfolioProperty property) {
        PortfolioFinance finance = property.getPortfolioFinance();
        double weeklyRent = finance.isRented() ? finance.getActualRent() : finance.getEstimatedRent();
        if (weeklyRent == 0 || finance.getPurchasePrice() == 0) {
            return 0;
        }

        return ((weeklyRent * 52) / finance.getPurchasePrice()) * 100;
    }

    public static double calculateLvr(PortfolioProperty property) {
        PortfolioFinance finance = property.getPortfolioFinance();
        double loanAmount = finance.getLoanAmount();
        double price = finance.getPurchasePrice();

        if (loanAmount == 0 || price == 0) {
            return 0;
        }

        return (loanAmount / price) * 100;
    }

    public static double calculateValueChange(PortfolioProperty property) {
        PortfolioFinance finance = property.getPortfolioFinance();
        return finance.getEstimatedRent() - finance.getPurchasePrice();
    }
}
