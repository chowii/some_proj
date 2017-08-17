package com.soho.sohoapp.home.addproperty.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.PropertyRole;

import java.util.List;

public class RelationsDialog {
    private final Context context;

    public RelationsDialog(@NonNull Context context) {
        this.context = context;
    }

    public AlertDialog show(List<PropertyRole> propertyRoleList, Listener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.dialog_item);
        for (PropertyRole propertyRole : propertyRoleList) {
            arrayAdapter.add(propertyRole.getLabel());
        }

        builder.setAdapter(arrayAdapter, (dialog, which) -> listener.onRelationClicked(propertyRoleList.get(which)));
        return builder.show();
    }

    public interface Listener {
        void onRelationClicked(PropertyRole propertyRole);
    }
}
