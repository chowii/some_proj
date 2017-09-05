package com.soho.sohoapp.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.soho.sohoapp.R;

/**
 * Created by chowii on 28/8/17.
 */

public class TokenTextView extends AppCompatTextView {
    public TokenTextView(Context context) {
        super(context);
    }

    public TokenTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.ic_close_white_16dp: 0, 0);
        setCompoundDrawablePadding(4);
    }

}
