package com.soho.sohoapp.home.addproperty.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soho.sohoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberPickerView extends LinearLayout {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.decrease)
    Button decrease;
    @BindView(R.id.increase)
    Button increase;

    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_MAX_VALUE = 99;
    private static final int DEFAULT_STEP = 1;
    private int minValue = DEFAULT_MIN_VALUE;
    private int maxValue = DEFAULT_MAX_VALUE;
    private int step = DEFAULT_STEP;

    private int currentValue;
    private Listener listener;

    public NumberPickerView(Context context) {
        super(context);
        initView();
    }

    public NumberPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        validateButtons();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        validateButtons();
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        validateButtons();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    @OnClick(R.id.decrease)
    void decrease() {
        currentValue -= step;
        validateValue();
        validateButtons();
        listener.onValueChanged(currentValue);
    }


    @OnClick(R.id.increase)
    void increase() {
        currentValue += step;
        validateValue();
        validateButtons();
        listener.onValueChanged(currentValue);
    }

    private void validateValue() {
        if (currentValue < minValue) {
            currentValue = minValue;
        } else if (currentValue > maxValue) {
            currentValue = maxValue;
        }
    }

    private void validateButtons() {
        decrease.setEnabled(currentValue != minValue);
        increase.setEnabled(currentValue != maxValue);
    }

    private void initView() {
        inflate(getContext(), R.layout.number_picker_view, this);
        ButterKnife.bind(this);
        setOrientation(HORIZONTAL);

        currentValue = minValue + step;
    }

    public interface Listener {
        void onValueChanged(int currentValue);
    }
}
