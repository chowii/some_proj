package com.soho.sohoapp.navigator;

import android.support.annotation.NonNull;

import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;

public interface Navigator {

    void exitCurrentScreen();

    void exitWithResultCodeOk();

    void openAddPropertyScreen();

    void openAddPropertyScreen(int requestCode);

    void openEditPropertyScreen(PortfolioProperty property);

    void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory);

    void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory);
}
