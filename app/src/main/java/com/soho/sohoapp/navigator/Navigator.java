package com.soho.sohoapp.navigator;

import android.support.annotation.NonNull;

import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;

public interface Navigator {

    void exitCurrentScreen();

    void openAddPropertyScreen();

    void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory);

    void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory);
}
