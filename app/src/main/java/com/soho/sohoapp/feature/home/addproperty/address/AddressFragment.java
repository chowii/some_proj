package com.soho.sohoapp.feature.home.addproperty.address;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.extensions.ActivityExtKt;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.location.LocationProviderImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class AddressFragment extends BaseFragment implements AddressContract.ViewInteractable {
    public static final String TAG = AddressFragment.class.getSimpleName();
    private static final int GOOGLE_CLIENT_ID = 100;

    @BindView(R.id.autocomplete)
    AutoCompleteTextView autocomplete;

    private AddressContract.ViewPresentable presentable;
    private AddressPresenter presenter;
    private GoogleApiClient googleApiClient;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Fragment newInstance() {
        return new AddressFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAutocomplete();

        presenter = new AddressPresenter(this, LocationProviderImpl.newInstance(getContext(), googleApiClient));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        presenter = null;
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
            googleApiClient = null;
        }
        super.onDestroyView();
    }

    @Override
    public void setPresentable(AddressContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show(getString(R.string.common_loading));
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void showError(Throwable t) {
        handleError(t);
    }

    @Override
    public void setAddress(String address) {
        autocomplete.setText(address);
    }

    @Override
    public void showEmptyLocationError() {
        Toast.makeText(getContext(), R.string.add_property_empty_address_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getAddress() {
        return autocomplete.getText().toString();
    }

    @Override
    public void sendAddressToActivity(Location address) {
        Listener listener = (Listener) getActivity();
        listener.onAddressSelected(address);
    }

    @Override
    public void showKeyboard() {
        autocomplete.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    public void hideKeyboard() {
        ActivityExtKt.hideKeyboard(getActivity());
    }

    @OnClick(R.id.clearAddress)
    void clearAddress() {
        presentable.onClearClicked();
    }

    @OnClick(R.id.done)
    void done() {
        presentable.onDoneClicked();
    }

    private void initAutocomplete() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), GOOGLE_CLIENT_ID, connectionResult ->
                        DEPENDENCIES.getLogger().w("Connection failed"))
                .addApi(Places.GEO_DATA_API)
                .build();
        PlaceAutocompleteAdapter adapter = new PlaceAutocompleteAdapter(getActivity(), googleApiClient, null);
        autocomplete.setOnItemClickListener((parent, view, position, id) -> {
            AutocompletePrediction item = adapter.getItem(position);
            if (item != null) {
                presentable.onAddressClicked(item.getPlaceId(), item.getFullText(null).toString());
            }
        });
        autocomplete.setAdapter(adapter);
        autocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //not needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presentable.onAddressChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //not needed here
            }
        });
    }

    public interface Listener {
        void onAddressSelected(Location location);
    }
}
