package com.soho.sohoapp.home.addproperty.relation;

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

public class RelationFragment extends BaseFragment {
    public static final String TAG = RelationFragment.class.getSimpleName();

    @NonNull
    public static Fragment newInstance() {
        return new RelationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
