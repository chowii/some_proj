package com.soho.sohoapp.home.addproperty.investment;

public interface InvestmentContract {
    interface ViewActionsListener {
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);
    }
}
