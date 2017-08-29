package com.soho.sohoapp.customviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by chowii on 28/8/17.
 */

public class TokenizedSuburbAutoCompleteTextView extends TokenCompleteTextView {

    public TokenizedSuburbAutoCompleteTextView(Context context) {
        super(context);
    }

    public TokenizedSuburbAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TokenizedSuburbAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(Object object) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TokenTextView textView = (TokenTextView) inflater.inflate(R.layout.suburb_token_item, (ViewGroup) getParent(), false);
        textView.setText(object.toString());
        return textView;
    }

    @Override
    protected Object defaultObject(String completionText) {
        Log.d("LOG_TAG---", completionText);
        return completionText;
    }
}
