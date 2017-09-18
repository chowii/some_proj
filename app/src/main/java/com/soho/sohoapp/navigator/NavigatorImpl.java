package com.soho.sohoapp.navigator;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.soho.sohoapp.feature.home.HomeActivity;
import com.soho.sohoapp.feature.home.addproperty.AddPropertyActivity;
import com.soho.sohoapp.feature.home.editproperty.EditPropertyActivity;

import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.feature.home.editproperty.publish.privatestatus.PrivateStatusSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.PublicStatusSettingsActivity;
import com.soho.sohoapp.feature.home.more.SettingsActivity;
import com.soho.sohoapp.feature.home.more.VerifyAgentLicenseActivity;
import com.soho.sohoapp.feature.home.more.VerifyPhoneActivity;

import com.soho.sohoapp.data.models.Property;
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
    public static final String KEY_PROPERTY_LISTING = "KEY_PROPERTY_LISTING";
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
    public void exitWithResultCodeOk(@NonNull PropertyListing propertyListing) {
        Intent intent = new Intent();
        intent.putExtra(KEY_PROPERTY_LISTING, propertyListing);
        if (fragment != null) {
            FragmentActivity fragmentActivity = fragment.getActivity();
            fragmentActivity.setResult(Activity.RESULT_OK, intent);
            fragmentActivity.finish();
        } else {
            activity.setResult(Activity.RESULT_OK, intent);
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
    public void openPropertyStatusScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PropertyStatusActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(PropertyStatusActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openPrivateStatusSettingsScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PrivateStatusSettingsActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(PrivateStatusSettingsActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openPublicStatusSettingsScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PublicStatusSettingsActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(PublicStatusSettingsActivity.createIntent(activity, property), requestCode);
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

    @Override
    public void startCameraIntentForResult(int CAMERA_INTENT_REQUEST_CODE) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (activity == null) {
            if (intent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
                fragment.startActivityForResult(intent, CAMERA_INTENT_REQUEST_CODE);
            }
        } else {
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(intent, CAMERA_INTENT_REQUEST_CODE);
            }
        }
    }

    @Override
    public void startVerifyPhoneActivity() {
        if (fragment != null) {
            fragment.getActivity().startActivity(VerifyPhoneActivity.createIntent(fragment.getContext()));
        } else {
            activity.startActivity(VerifyPhoneActivity.createIntent(activity));
        }
    }

    @Override
    public void startVerifyPhoneActivity(int flag) {
        if (fragment != null) {
            fragment.getActivity().startActivity(VerifyPhoneActivity.createIntent(fragment.getContext(), flag));
        } else {
            activity.startActivity(VerifyPhoneActivity.createIntent(activity, flag));
        }
    }

    @Override
    public void openSettingActivity() {
        if (fragment != null) {
            fragment.getActivity().startActivity(SettingsActivity.createIntent(fragment.getContext()));
        } else {
            activity.startActivity(SettingsActivity.createIntent(activity));
        }
    }

    @Override
    public void startAgentLicenseActivity(int flag) {
        if (fragment != null) {
            fragment.getActivity().startActivity(VerifyAgentLicenseActivity.createIntent(fragment.getContext()));
        } else {
            activity.startActivity(VerifyAgentLicenseActivity.createIntent(activity));
        }
    }
}
