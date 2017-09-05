package com.soho.sohoapp.feature.home.addproperty.relation;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;

import java.util.List;

public interface RelationContract {

    interface ViewPresentable {
        void onOwnerSelected();

        void onAgentSelected();

        void onOtherClicked();

        void onOtherTypeSelected(PropertyRole propertyRole);
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showRelationDialog(List<PropertyRole> relationList);

        void showError(Throwable t);

        void showLoadingDialog();

        void hideLoadingDialog();

        void sendRoleToActivity(PropertyRole propertyRole);
    }
}
