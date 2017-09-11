package com.soho.sohoapp.feature.home.editproperty.connections;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.landing.BaseFragment;

import butterknife.ButterKnife;

public class EditConnectionFragment extends BaseFragment {

    @NonNull
    public static Fragment newInstance() {
        return new EditConnectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_connection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
