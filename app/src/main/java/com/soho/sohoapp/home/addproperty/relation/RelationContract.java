package com.soho.sohoapp.home.addproperty.relation;

import com.soho.sohoapp.home.addproperty.data.PropertyRole;

import java.util.List;

public interface RelationContract {

    interface ViewActionsListener {
        void onOwnerSelected();

        void onAgentSelected();

        void onOtherClicked();

        void onOtherTypeSelected(PropertyRole propertyRole);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showRelationDialog(List<PropertyRole> relationList);

        void showError(Throwable t);

        void showLoadingDialog();

        void hideLoadingDialog();

        void sendRoleToActivity(PropertyRole propertyRole);
    }
}
