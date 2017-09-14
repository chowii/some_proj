package com.soho.sohoapp.navigator;

import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;

public interface NavigatorInterface {

    void exitCurrentScreen();

    void exitWithResultCodeOk();

    void openAddPropertyScreen();

    void openAddPropertyScreen(int requestCode);

    void openEditPropertyScreen(PortfolioProperty property);

    void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory);

    void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory);

    void openPropertyStatusScreen(@NonNull Property property);

    void openHomeActivity();

    void openHomeActivity(int flags);

    void openLandingActivity();

    void openLandingActivity(int flags);

    void openForgetPasswordActivity();

    void openSignUpActivity();

    void showRegisterUserInfoActivity();

    void showLandingActivity();

}
