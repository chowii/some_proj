package com.soho.sohoapp.feature.home.portfolio.holders;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.portfolio.data.Title;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleHolder extends BaseViewHolder<Title> {
    @BindView(R.id.title)
    TextView title;

    public TitleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(Title model) {
        title.setText(model.getTitle());
    }

}
