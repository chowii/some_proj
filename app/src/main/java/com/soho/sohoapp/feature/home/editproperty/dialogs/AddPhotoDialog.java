package com.soho.sohoapp.feature.home.editproperty.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.soho.sohoapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddPhotoDialog {
    private final Context context;
    private final String takeNewPhoto;
    private final String chooseFromGallery;

    public AddPhotoDialog(@NonNull Context context) {
        this.context = context;
        takeNewPhoto = context.getString(R.string.edit_property_take_a_photo);
        chooseFromGallery = context.getString(R.string.edit_property_upload_a_photo);
    }

    public AlertDialog show(OnItemClickedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.dialog_item);
        List<String> itemList = createItems();
        arrayAdapter.addAll(itemList);

        builder.setAdapter(arrayAdapter, (dialog, which) -> {
            String selectedItem = itemList.get(which);
            if (takeNewPhoto.equals(selectedItem)) {
                listener.onTakeNewPhotoClicked();
            } else if (chooseFromGallery.equals(selectedItem)) {
                listener.onChooseFromGalleryClicked();
            }
        });
        return builder.show();
    }

    private List<String> createItems() {
        List<String> listItems = new ArrayList<>();
        listItems.add(takeNewPhoto);
        listItems.add(chooseFromGallery);
        return listItems;
    }

    public interface OnItemClickedListener {
        void onTakeNewPhotoClicked();

        void onChooseFromGalleryClicked();
    }
}
