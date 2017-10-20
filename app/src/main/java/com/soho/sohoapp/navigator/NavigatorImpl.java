package com.soho.sohoapp.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.InspectionTime;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.Verification;
import com.soho.sohoapp.feature.gallery.GalleryViewActivity;
import com.soho.sohoapp.feature.home.HomeActivity;
import com.soho.sohoapp.feature.home.addproperty.AddPropertyActivity;
import com.soho.sohoapp.feature.home.editproperty.EditPropertyActivity;
import com.soho.sohoapp.feature.home.editproperty.archive.ArchiveConfirmationActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.PropertyDescriptionActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.PropertyStatusActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.privatestatus.PrivateStatusSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.AutocompleteAddressActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.PublicStatusSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.discoverable.DiscoverableSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection.InspectionTimeActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection.NewInspectionTimeActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent.RentSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.sale.SaleAndAuctionSettingsActivity;
import com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.updated.PropertyStatusUpdatedActivity;
import com.soho.sohoapp.feature.home.editproperty.verification.VerificationActivity;
import com.soho.sohoapp.feature.home.editproperty.verification.ownership.OwnershipVerificationActivity;
import com.soho.sohoapp.feature.home.more.SettingsActivity;
import com.soho.sohoapp.feature.home.more.VerifyAgentLicenseActivity;
import com.soho.sohoapp.feature.home.more.verifyNumber.EnterPinActivity;
import com.soho.sohoapp.feature.home.more.verifyNumber.VerifyPhoneActivity;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.details.PortfolioDetailsActivity;
import com.soho.sohoapp.feature.landing.ForgotPasswordActivity;
import com.soho.sohoapp.feature.landing.LandingActivity;
import com.soho.sohoapp.feature.landing.signup.RegisterUserInfoActivity;
import com.soho.sohoapp.feature.landing.signup.SignUpActivity;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.PropertyDetailActivity;
import com.soho.sohoapp.feature.profile.EditAccountActivity;
import com.soho.sohoapp.feature.profile.password.EditAccountPassActivity;
import com.zendesk.sdk.feedback.ui.ContactZendeskActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NavigatorImpl implements NavigatorInterface {
    public static final String KEY_PROPERTY = "KEY_PROPERTY";
    public static final String KEY_PROPERTY_LOCATION = "KEY_PROPERTY_LOCATION";
    public static final String KEY_STRING = "KEY_STRING";
    public static final String KEY_VERIFICATION_COMPLETED = "KEY_VERIFICATION_COMPLETED";
    public static final String KEY_VERIFICATION_UPDATED = "KEY_VERIFICATION_UPDATED";
    public static final String KEY_VERIFICATIONS_UPDATED = "KEY_VERIFICATIONS_UPDATED";
    public static final String KEY_INSPECTION_TIME = "KEY_INSPECTION_TIME";
    public static final String KEY_INSPECTION_TIME_IS_CREATED = "KEY_INSPECTION_TIME_IS_CREATED";
    private Activity activity;
    private Fragment fragment;

    //in case we want the result on the Activity
    private NavigatorImpl(@NonNull Activity activity) {
        this.activity = activity;
    }

    //in case we want the result on the Fragment
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
    public void exitWithResultCodeOk(@NonNull Verification verification) {
        Intent intent = new Intent();
        intent.putExtra(KEY_VERIFICATION_UPDATED, verification);
        setResultAndExit(intent);
    }

    @Override
    public void exitWithResultCodeOk(@NonNull Property property, boolean verificationCompleted) {
        Intent intent = new Intent();
        intent.putExtra(KEY_PROPERTY, property);
        intent.putExtra(KEY_VERIFICATION_COMPLETED, verificationCompleted);
        setResultAndExit(intent);
    }

    @Override
    public void exitWithResultCodeOk(@NonNull Location location) {
        Intent intent = new Intent();
        intent.putExtra(KEY_PROPERTY_LOCATION, location);
        setResultAndExit(intent);
    }

    @Override
    public void exitWithResultCodeOk(InspectionTime inspectionTime, boolean inspectionTimeIsCreated) {
        Intent intent = new Intent();
        intent.putExtra(KEY_INSPECTION_TIME, inspectionTime);
        intent.putExtra(KEY_INSPECTION_TIME_IS_CREATED, inspectionTimeIsCreated);
        setResultAndExit(intent);
    }

    @Override
    public void exitWithResultCodeOk(String string) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STRING, string);
        setResultAndExit(intent);
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
    public void openGallery(List<Image> images, int currentItemPos) {
        if (fragment != null) {
            fragment.startActivity(GalleryViewActivity.createIntent(fragment.getActivity()
                    , images, currentItemPos));
        } else {
            activity.startActivity(GalleryViewActivity.createIntent(activity, images, currentItemPos));
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
    public void openEditProfileScreen(int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(EditAccountActivity.createIntent(fragment.getActivity()), requestCode);
        } else {
            activity.startActivityForResult(EditAccountActivity.createIntent(activity), requestCode);
        }
    }

    @Override
    public void openEditPasswordProfileScreen(int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(EditAccountPassActivity.createIntent(fragment.getActivity()), requestCode);
        } else {
            activity.startActivityForResult(EditAccountPassActivity.createIntent(activity), requestCode);
        }
    }

    @Override
    public void openEditPropertyScreen(int propertyID) {
        if (fragment != null) {
            fragment.startActivity(EditPropertyActivity.createIntent(fragment.getActivity(), propertyID));
        } else {
            activity.startActivity(EditPropertyActivity.createIntent(activity, propertyID));
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
    public void openSaleAndAuctionSettingsScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(SaleAndAuctionSettingsActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(SaleAndAuctionSettingsActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openRentSettingsScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(RentSettingsActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(RentSettingsActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openDiscoverableSettingsScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(DiscoverableSettingsActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(DiscoverableSettingsActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openPropertyStatusUpdatedScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PropertyStatusUpdatedActivity.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(PropertyStatusUpdatedActivity.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openInspectionTimeScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(InspectionTimeActivity.Companion.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(InspectionTimeActivity.Companion.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openNewInspectionTimeScreen(@Nullable InspectionTime inspectionTime, int propertyId, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(
                    NewInspectionTimeActivity.Companion.createIntent(fragment.getActivity(), inspectionTime, propertyId), requestCode);
        } else {
            activity.startActivityForResult(NewInspectionTimeActivity.Companion.createIntent(activity, inspectionTime, propertyId), requestCode);
        }
    }

    @Override
    public void openVerificationScreen(@NonNull Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(VerificationActivity.Companion.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(VerificationActivity.Companion.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openOwnershipVerificationScreen(Property property, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(OwnershipVerificationActivity.Companion.createIntent(fragment.getActivity(), property), requestCode);
        } else {
            activity.startActivityForResult(OwnershipVerificationActivity.Companion.createIntent(activity, property), requestCode);
        }
    }

    @Override
    public void openAutocompleteAddressScreen(int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(AutocompleteAddressActivity.createIntent(fragment.getActivity()), requestCode);
        } else {
            activity.startActivityForResult(AutocompleteAddressActivity.createIntent(activity), requestCode);
        }
    }

    @Override
    public void openAutocompleteAddressScreen(int requestCode, boolean showConfirmationDialog) {
        if (fragment != null) {
            fragment.startActivityForResult(AutocompleteAddressActivity.createIntent(fragment.getActivity(), showConfirmationDialog), requestCode);
        } else {
            activity.startActivityForResult(AutocompleteAddressActivity.createIntent(activity, showConfirmationDialog), requestCode);
        }
    }

    @Override
    public void openPropertyDescriptionScreen(String description, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PropertyDescriptionActivity.createIntent(fragment.getActivity(), description), requestCode);
        } else {
            activity.startActivityForResult(PropertyDescriptionActivity.createIntent(activity, description), requestCode);
        }
    }

    @Override
    public void openPropertyDescriptionScreen(String description, boolean forRenovation, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(PropertyDescriptionActivity.createIntent(fragment.getActivity(), forRenovation, description), requestCode);
        } else {
            activity.startActivityForResult(PropertyDescriptionActivity.createIntent(activity, forRenovation, description), requestCode);
        }
    }

    @Override
    public void openArchiveConfirmationScreen(String propertyAddress, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(ArchiveConfirmationActivity.Companion.createIntent(fragment.getActivity(), propertyAddress), requestCode);
        } else {
            activity.startActivityForResult(ArchiveConfirmationActivity.Companion.createIntent(activity, propertyAddress), requestCode);
        }
    }

    @Override
    public void openHomeActivity() {
        if (fragment != null) {
            Intent intent = HomeActivity.createIntent(fragment.getActivity());
            fragment.getActivity().finishAffinity();
            fragment.startActivity(intent);
        } else {
            Intent intent = HomeActivity.createIntent(activity);
            activity.finishAffinity();
            activity.startActivity(intent);
        }
    }

    @Override
    public void openHomeActivity(int flags) {
        if (fragment != null) {
            Intent intent = HomeActivity.createIntent(fragment.getActivity());
            fragment.getActivity().finishAffinity();
            fragment.startActivity(intent);
        } else {
            Intent intent = HomeActivity.createIntent(activity);
            activity.finishAffinity();
            activity.startActivity(intent);
        }
    }

    public void openHomeActivityDeepLinking(@NotNull Uri data) {
        if (fragment != null) {
            Intent intent = HomeActivity.createIntent(fragment.getActivity());
            intent.setData(data);
            fragment.getActivity().finishAffinity();
            fragment.startActivity(intent);
        } else {
            Intent intent = HomeActivity.createIntent(activity);
            intent.setData(data);
            activity.finishAffinity();
            activity.startActivity(intent);
        }
    }


    @Override
    public void openForgetPasswordActivity() {
        if (fragment != null) {
            fragment.startActivity(ForgotPasswordActivity.Companion.createIntent(fragment.getActivity()));
        } else {
            activity.startActivity(ForgotPasswordActivity.Companion.createIntent(activity));
        }
    }

    @Override
    public void openSignUpActivity() {
        if (fragment != null) {
            Intent intent = SignUpActivity.Companion.createIntent(fragment.getActivity());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            fragment.startActivity(intent);
        } else {
            Intent intent = SignUpActivity.Companion.createIntent(activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }
    }

    @Override
    public void openRegisterUserInfoActivity() {
        if (fragment != null) {
            Intent intent = RegisterUserInfoActivity.Companion.createIntent(fragment.getActivity());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            fragment.startActivity(intent);
        } else {
            Intent intent = RegisterUserInfoActivity.Companion.createIntent(activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }
    }

    @Override
    public void openLandingActivity() {
        if (fragment != null) {
            Intent intent = LandingActivity.Companion.createIntent(fragment.getActivity());
            fragment.getActivity().finishAffinity();
            fragment.startActivity(intent);
        } else {
            Intent intent = LandingActivity.Companion.createIntent(activity);
            activity.finishAffinity();
            activity.startActivity(intent);
        }
    }

    @Override
    public void openCameraIntentForResult(int CAMERA_INTENT_REQUEST_CODE) {
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
    public void openVerifyPhoneActivity() {
        if (fragment != null) {
            fragment.getActivity().startActivity(VerifyPhoneActivity.createIntent(fragment.getContext()));
        } else {
            activity.startActivity(VerifyPhoneActivity.createIntent(activity));
        }
    }

    @Override
    public void openEnterPinActivity(String phoneNumber) {
        if (fragment != null) {
            Intent intent = EnterPinActivity.createIntent(fragment.getContext(), phoneNumber);
            fragment.getActivity().startActivity(intent);
        } else {
            Intent intent = EnterPinActivity.createIntent(activity, phoneNumber);
            activity.startActivity(intent);
        }
    }

    @Override
    public void openVerifyPhoneActivity(int flag) {
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
    public void openAgentLicenseActivity(int flag) {
        if (fragment != null) {
            fragment.getActivity().startActivity(VerifyAgentLicenseActivity.createIntent(fragment.getContext()));
        } else {
            activity.startActivity(VerifyAgentLicenseActivity.createIntent(activity));
        }
    }

    @Override
    public void openHelpActivity() {
        if (fragment != null) {
            ContactZendeskActivity.startActivity(fragment.getContext(), null);
        } else {
            ContactZendeskActivity.startActivity(activity, null);
        }
    }

    @Override
    public void openPropertyDetailScreen(int id) {
        if (fragment != null) {
            fragment.getActivity().startActivity(PropertyDetailActivity.createIntent(fragment.getContext(), id));
        } else {
            activity.startActivity(PropertyDetailActivity.createIntent(activity, id));
        }
    }

    @Override
    public void openExternalUrl(@Nullable Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (fragment != null) {
            fragment.getActivity().startActivity(intent);
        } else {
            activity.startActivity(intent);
        }
    }

    private void setResultAndExit(Intent intent) {
        if (fragment != null) {
            FragmentActivity fragmentActivity = fragment.getActivity();
            fragmentActivity.setResult(Activity.RESULT_OK, intent);
            fragmentActivity.finish();
        } else {
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }
}
