package com.soho.sohoapp.home.addproperty.type;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.PropertyType;
import com.soho.sohoapp.landing.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyTypeFragment extends BaseFragment implements PropertyTypeContract.View {
    public static final String TAG = PropertyTypeFragment.class.getSimpleName();

    @BindView(R.id.typeList)
    RecyclerView typeList;

    @BindView(R.id.progress)
    ProgressBar progress;

    private PropertyTypeContract.ViewActionsListener actionsListener;
    private PropertyTypePresenter presenter;
    private PropertyTypeAdapter adapter;

    @NonNull
    public static Fragment newInstance() {
        return new PropertyTypeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_type, container, false);
        ButterKnife.bind(this, view);

        initPropertyTypeList();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new PropertyTypePresenter(this);
        presenter.startPresenting(savedInstanceState!=null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void setActionsListener(PropertyTypeContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void setTypeList(@NonNull List<PropertyType> typeList) {
        adapter.setData(typeList);
    }

    @Override
    public void showError(Throwable t) {
        handleError(t);
    }

    @Override
    public void sendTypeToActivity(PropertyType propertyType) {
        Listener listener = (Listener) getActivity();
        listener.onPropertyTypeSelected(propertyType);
    }

    @Override
    public void showLoadingIndicator() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progress.setVisibility(View.GONE);
    }

    private void initPropertyTypeList() {
        adapter = new PropertyTypeAdapter();
        adapter.setListener(propertyType -> actionsListener.onPropertySelected(propertyType));
        typeList.setAdapter(adapter);
        typeList.setHasFixedSize(true);
        typeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
    }

    public interface Listener {
        void onPropertyTypeSelected(PropertyType propertyType);
    }

}
