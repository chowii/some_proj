package com.soho.sohoapp.navigator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.soho.sohoapp.feature.home.HomeActivity;
import com.soho.sohoapp.feature.home.addproperty.AddPropertyActivity;
import com.soho.sohoapp.feature.home.editproperty.EditPropertyActivity;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.publish.PropertyStatusActivity;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.feature.home.portfolio.details.PortfolioDetailsActivity;
import com.soho.sohoapp.feature.landing.ForgotPasswordActivity;
import com.soho.sohoapp.feature.landing.LandingActivity;
import com.soho.sohoapp.feature.landing.signup.RegisterUserInfoActivity;
import com.soho.sohoapp.feature.landing.signup.SignUpActivity;

public class NavigatorImpl implements NavigatorInterface {
    private Activity activity;
    private Fragment fragment;

    private NavigatorImpl(@NonNull Activity activity) {
        this.activity = activity;
    }

    private NavigatorImpl(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    public static NavigatorImpl newInstance(@NonNull Activity activity) {
        return new NavigatorImpl(activity);
    }

    public static NavigatorImpl newInstance(@NonNull Fragment fragment) {
        return new NavigatorImpl(fragment);
    }

    @Override
    public void exitCurrentScreen() {
        if (fragment != null) {
            fragment.getActivity().finish();
        } else {
            activity.finish();
        }
    }

    @Override
    public void exitWithResultCodeOk() {
        if (fragment != null) {
            FragmentActivity fragmentActivity = fragment.getActivity();
            fragmentActivity.setResult(Activity.RESULT_OK);
            fragmentActivity.finish();
        } else {
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    }

    @Override
    public void openAddPropertyScreen() {
        if (fragment != null) {
            fragment.startActivity(AddPropertyActivity.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(AddPropertyActivity.createIntent(activity));
        }
    }

    @Override
    public void openAddPropertyScreen(int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(AddPropertyActivity.createIntent(fragment.getActivity()), requestCode);
        } else {
            activity.startActivityForResult(AddPropertyActivity.createIntent(activity), requestCode);
        }
    }

    @Override
    public void openEditPropertyScreen(PortfolioProperty property) {
        if (fragment != null) {
            fragment.startActivity(EditPropertyActivity.createIntent(fragment.getActivity(), property));
        } else {
            activity.startActivity(EditPropertyActivity.createIntent(activity, property));
        }
    }

    @Override
    public void openOwnerPortfolioDetails(@NonNull PortfolioCategory portfolioCategory) {
        if (fragment != null) {
            fragment.startActivity(PortfolioDetailsActivity.createOwnerIntent(fragment.getActivity(), portfolioCategory));
        } else {
            activity.startActivity(PortfolioDetailsActivity.createOwnerIntent(activity, portfolioCategory));
        }
    }

    @Override
    public void openManagerPortfolioDetails(@NonNull PortfolioManagerCategory portfolioCategory) {
        if (fragment != null) {
            fragment.startActivity(PortfolioDetailsActivity.createManagerIntent(fragment.getActivity(), portfolioCategory));
        } else {
            activity.startActivity(PortfolioDetailsActivity.createManagerIntent(activity, portfolioCategory));
        }
    }

    @Override
    public void openPropertyStatusScreen(@NonNull Property property) {
        if (fragment != null) {
            fragment.startActivity(PropertyStatusActivity.createIntent(fragment.getActivity(), property));
        } else {
            activity.startActivity(PropertyStatusActivity.createIntent(activity, property));
        }
    }

    @Override
    public void openHomeActivity() {
        if (fragment != null) {
            fragment.startActivity(HomeActivity.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(HomeActivity.createIntent(activity));
        }
    }

    @Override
    public void openHomeActivity(int flags) {
        if (fragment != null) {
            fragment.startActivity(HomeActivity.createIntent(fragment.getActivity(), flags));
        } else {
            activity.startActivity(HomeActivity.createIntent(activity, flags));
        }
    }

    public void openLandingActivity() {
        if (fragment != null) {
            fragment.startActivity(LandingActivity.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(LandingActivity.createIntent(activity));
        }
    }

    public void openLandingActivity(int flags) {
        if (fragment != null) {
            fragment.startActivity(LandingActivity.createIntent(fragment.getActivity(), flags));
        } else {
            activity.startActivity(LandingActivity.createIntent(activity, flags));
        }
    }

    @Override
    public void openForgetPasswordActivity() {
        if (fragment != null) {
            fragment.startActivity(ForgotPasswordActivity.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(ForgotPasswordActivity.createIntent(activity));
        }
    }

    @Override
    public void openSignUpActivity() {
        if (fragment != null) {
            fragment.startActivity(SignUpActivity.Companion.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(SignUpActivity.Companion.createIntent(activity));
        }
    }

    @Override
    public void showRegisterUserInfoActivity() {
        if (fragment != null) {
            fragment.startActivity(RegisterUserInfoActivity.Companion.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(RegisterUserInfoActivity.Companion.createIntent(activity));
        }
    }

    @Override
    public void showLandingActivity() {
        if (fragment != null) {
            fragment.startActivity(LandingActivity.Companion.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(LandingActivity.Companion.createIntent(activity));
        }
    }
}
