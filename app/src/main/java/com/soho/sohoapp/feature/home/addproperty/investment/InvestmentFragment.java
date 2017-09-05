package com.soho.sohoapp.feature.home.addproperty.investment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.landing.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvestmentFragment extends BaseFragment implements InvestmentContract.ViewInteractable {
    public static final String TAG = InvestmentFragment.class.getSimpleName();
    private static final String KEY_FOR_OWNER = "KEY_FOR_OWNER";

    @BindView(R.id.question)
    TextView question;

    @BindView(R.id.home)
    Button home;

    @BindView(R.id.investment)
    Button investment;

    private InvestmentContract.ViewPresentable presentable;
    private InvestmentPresenter presenter;

    @NonNull
    public static Fragment newInstance(boolean forOwner) {
        Fragment fragment = new InvestmentFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_FOR_OWNER, forOwner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new InvestmentPresenter(this);
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void setPresentable(InvestmentContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public boolean isForOwner() {
        return getArguments().getBoolean(KEY_FOR_OWNER);
    }

    @Override
    public void showOwnerQuestion() {
        question.setText(R.string.add_property_owner_home_question);
    }

    @Override
    public void showAgentQuestion() {
        question.setText(R.string.add_property_agent_home_question);
    }

    @Override
    public void showOwnerHomeButton() {
        home.setText(R.string.add_property_owner_home_button);
    }

    @Override
    public void showOwnerInvestmentButton() {
        investment.setText(R.string.add_property_owner_investment_button);
    }

    @Override
    public void showAgentHomeButton() {
        home.setText(R.string.add_property_agent_home_button);
    }

    @Override
    public void showAgentInvestmentButton() {
        investment.setText(R.string.add_property_agent_investment_button);
    }

    @Override
    public void sendResultToActivity(boolean isInvestment) {
        Listener listener = (Listener) getActivity();
        listener.onHomeOrInvestmentSelected(isInvestment);
    }

    @OnClick(R.id.home)
    void onHomeClicked() {
        presentable.onHomeClicked();
    }

    @OnClick(R.id.investment)
    void onInvestmentClicked() {
        presentable.onInvestmentClicked();
    }

    public interface Listener {
        void onHomeOrInvestmentSelected(boolean isInvestment);
    }
}
