package com.soho.sohoapp.navigator;

import android.support.annotation.NonNull;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;

public interface NavigatorInterface {

    void exitCurrentScreen();

    void exitWithResultCodeOk();

    void exitWithResultCodeOk(@NonNull PropertyListing propertyListing);

    void openAddPropertyScreen();

    void openAddPropertyScreen(int requestCode);

    void openEditPropertyScreen(PortfolioProperty property);

    void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory);

    void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory);

    void openPropertyStatusScreen(@NonNull Property property, int requestCode);

    void openPrivateStatusSettingsScreen(@NonNull Property property, int requestCode);

    void openPublicStatusSettingsScreen(@NonNull Property property, int requestCode);

    void openHomeActivity();

    void openHomeActivity(int flags);

    void openLandingActivity();

    void openLandingActivity(int flags);

    void openForgetPasswordActivity();

    void openSignUpActivity();

    void showRegisterUserInfoActivity();

    void showLandingActivity();

    void startCameraIntentForResult(int CAMERA_INTENT_REQUEST_CODE);

    void startVerifyPhoneActivity();

    void startVerifyPhoneActivity(int flag);

    void openSettingActivity();

    void startAgentLicenseActivity(int flag);
}
