package com.soho.sohoapp.navigator;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.soho.sohoapp.home.addproperty.AddPropertyActivity;
import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.home.portfolio.details.PortfolioDetailsActivity;

public class AndroidNavigator implements Navigator {
    private Activity activity;

    private AndroidNavigator(@NonNull Activity activity) {
        this.activity = activity;
    }

    public static AndroidNavigator newInstance(@NonNull Activity activity) {
        return new AndroidNavigator(activity);
    }

    @Override
    public void exitCurrentScreen() {
        activity.finish();
    }

    @Override
    public void openAddPropertyScreen() {
        activity.startActivity(AddPropertyActivity.createIntent(activity));
    }

    @Override
    public void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory) {
        activity.startActivity(PortfolioDetailsActivity.createOwnerIntent(activity, portfolioCategory));
    }

    @Override
    public void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory) {
        activity.startActivity(PortfolioDetailsActivity.createManagerIntent(activity, portfolioCategory));
    }

}
