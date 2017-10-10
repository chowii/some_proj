package com.soho.sohoapp.feature.home.addproperty.rooms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.addproperty.views.RoomsNumberPickerView;
import com.soho.sohoapp.landing.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomsFragment extends BaseFragment {
    public static final String TAG = RoomsFragment.class.getSimpleName();
    @BindView(R.id.roomsSelector)
    RoomsNumberPickerView roomsSelector;

    @NonNull
    public static Fragment newInstance() {
        return new RoomsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.addProperty)
    void addProperty() {
        RoomsSelectedListener listener = (RoomsSelectedListener) getActivity();
        listener.onRoomsSelected(roomsSelector.getBedroomsCount(), roomsSelector.getBathroomsCount(), roomsSelector.getCarspotsCount());
    }

    public interface RoomsSelectedListener {
        void onRoomsSelected(int bedrooms, int bathrooms, int carspots);
    }

}
