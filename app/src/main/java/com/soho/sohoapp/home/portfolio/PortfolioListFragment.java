package com.soho.sohoapp.home.portfolio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.landing.BaseFragment;

import butterknife.ButterKnife;

public class PortfolioListFragment extends BaseFragment implements PortfolioListContract.View {
    private static final String KEY_MODE = "KEY_MODE";
    private PortfolioListContract.ViewActionsListener actionsListener;
    private AbsPresenter presenter;

    @NonNull
    public static Fragment newInstance(@NonNull Mode mode) {
        PortfolioListFragment fragment = new PortfolioListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MODE, mode.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_owner, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (getMode()) {
            case OWNER:
                presenter = new PortfolioOwnerPresenter(this);
                break;
            case MANAGER:
                presenter = new PortfolioManagerPresenter(this);
                break;
            default:
                throw new IllegalStateException();
        }

        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void setActionsListener(PortfolioListContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    private Mode getMode() {
        return Mode.valueOf(getArguments().getString(KEY_MODE));
    }

    public enum Mode {
        OWNER, MANAGER
    }
}
