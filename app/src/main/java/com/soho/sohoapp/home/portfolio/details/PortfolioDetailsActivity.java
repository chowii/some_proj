package com.soho.sohoapp.home.portfolio.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.navigator.AndroidNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioDetailsActivity extends AbsActivity implements PortfolioDetailsContract.View {
    private static final String KEY_OWNER_PORTFOLIO = "KEY_OWNER_PORTFOLIO";
    private static final String KEY_MANAGER_PORTFOLIO = "KEY_MANAGER_PORTFOLIO";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PortfolioDetailsContract.ViewActionsListener actionsListener;
    private PortfolioDetailsPresenter presenter;

    @NonNull
    public static Intent createOwnerIntent(@NonNull Context context, @NonNull PortfolioCategory portfolioCategory) {
        Intent intent = new Intent(context, PortfolioDetailsActivity.class);
        intent.putExtra(KEY_OWNER_PORTFOLIO, portfolioCategory);
        return intent;
    }

    @NonNull
    public static Intent createManagerIntent(@NonNull Context context, @NonNull PortfolioManagerCategory portfolioCategory) {
        Intent intent = new Intent(context, PortfolioDetailsActivity.class);
        intent.putExtra(KEY_MANAGER_PORTFOLIO, portfolioCategory);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_details);
        ButterKnife.bind(this);
        initToolbar();

        presenter = new PortfolioDetailsPresenter(this, AndroidNavigator.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setActionsListener(PortfolioDetailsContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public boolean isOwnerScreen() {
        return getIntent().hasExtra(KEY_OWNER_PORTFOLIO);
    }

    @Override
    public PortfolioCategory getOwnerPortfolio() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }
        return (PortfolioCategory) extras.getParcelable(KEY_OWNER_PORTFOLIO);
    }

    @Override
    public PortfolioManagerCategory getManagerPortfolio() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }
        return (PortfolioManagerCategory) extras.getParcelable(KEY_MANAGER_PORTFOLIO);
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(v -> actionsListener.onBackClicked());
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (intent.hasExtra(KEY_OWNER_PORTFOLIO)) {
                PortfolioCategory portfolio = extras.getParcelable(KEY_OWNER_PORTFOLIO);
                if (portfolio != null) {
                    toolbar.setTitle(portfolio.getName());
                }
            } else {
                PortfolioManagerCategory portfolio = extras.getParcelable(KEY_MANAGER_PORTFOLIO);
                if (portfolio != null) {
                    toolbar.setTitle(portfolio.getName());
                }
            }
        }
    }
}
