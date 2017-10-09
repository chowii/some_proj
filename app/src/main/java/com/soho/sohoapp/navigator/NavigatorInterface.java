package com.soho.sohoapp.navigator;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;

import org.jetbrains.annotations.Nullable;

public interface NavigatorInterface {

    void exitCurrentScreen();

    void exitWithResultCodeOk();

    void exitWithResultCodeOk(@NonNull Property property, boolean verificationCompleted);

    void exitWithResultCodeOk(@NonNull Location location);

    void exitWithResultCodeOk(String string);

    void openAddPropertyScreen();

    void openAddPropertyScreen(int requestCode);

    void openEditProfileScreen(int requestCode);

    void openEditPasswordProfileScreen(int requestCode);

    void openEditPropertyScreen(PortfolioProperty property);

    void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory);

    void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory);

    void openPropertyStatusScreen(@NonNull Property property, int requestCode);

    void openPrivateStatusSettingsScreen(@NonNull Property property, int requestCode);

    void openPublicStatusSettingsScreen(@NonNull Property property, int requestCode);

    void openSaleAndAuctionSettingsScreen(@NonNull Property property, int requestCode);

    void openRentSettingsScreen(@NonNull Property property, int requestCode);

    void openDiscoverableSettingsScreen(@NonNull Property property, int requestCode);

    void openPropertyStatusUpdatedScreen(@NonNull Property property, int requestCode);

    void openVerificationScreen(@NonNull Property property);

    void openOwnershipVerificationScreen(Property property);

    void openAutocompleteAddressScreen(int requestCode);

    void openPropertyDescriptionScreen(String description, int requestCode);

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

    void startEnterPinActivity(String phoneNumber);

    void startVerifyPhoneActivity(int flag);

    void openSettingActivity();

    void startAgentLicenseActivity(int flag);

    void openHelpActivity();

    void openExternalUrl(@Nullable Uri uri);
}
