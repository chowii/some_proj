package com.soho.sohoapp.feature.home.editproperty.overview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.utils.DateUtils;
import com.soho.sohoapp.utils.PropertyCalculator;
import com.soho.sohoapp.utils.StringUtils;
import com.soho.sohoapp.utils.TextWatcherAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.soho.sohoapp.extensions.StringExtKt.toDoubleOrDefault;
import static com.soho.sohoapp.extensions.StringExtKt.toFormattedNumberValue;
import static com.soho.sohoapp.extensions.StringExtKt.withCurrency;
import static com.soho.sohoapp.utils.StringUtils.shortFormatLvr;
import static com.soho.sohoapp.utils.StringUtils.shortFormatYield;

public class InvestmentSummaryView extends LinearLayout {
    private static final String TAG_DATE_PICKER_DIALOG = "TAG_DATE_PICKER_DIALOG";

    @BindView(R.id.yield)
    LinearLayout yield;

    @BindView(R.id.lvr)
    LinearLayout lvr;

    @BindView(R.id.valueChange)
    LinearLayout valueChange;

    @BindView(R.id.purchasePrice)
    LinearLayout purchasePrice;

    @BindView(R.id.loanAmount)
    LinearLayout loanAmount;

    @BindView(R.id.estimatedValue)
    LinearLayout estimatedValue;

    @BindView(R.id.weeklyRent)
    LinearLayout weeklyRent;

    @BindView(R.id.estimatedWeeklyRent)
    LinearLayout estimatedWeeklyRent;

    @BindView(R.id.endOfRent)
    LinearLayout endOfRent;

    @BindView(R.id.endDateOfRent)
    TextView endDateOfRent;

    @BindView(R.id.rented)
    RadioButton rented;

    @BindView(R.id.notRented)
    RadioButton notRented;

    @BindView(R.id.rentOptions)
    RadioGroup rentOptions;

    private TextView yieldValue;
    private TextView lvrValue;
    private TextView valueChangeValue;
    private EditText purchasePriceValue;
    private EditText loanAmountValue;
    private EditText estimatedValueValue;
    private EditText weeklyRentValue;
    private EditText estimatedWeeklyRentValue;
    private PropertyFinance finance;
    private OnPropertyFinanceChangedListener listener;

    public InvestmentSummaryView(Context context) {
        super(context);
        initView();
    }

    public InvestmentSummaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setListener(OnPropertyFinanceChangedListener listener) {
        this.listener = listener;
    }

    public void setPropertyFinance(@NonNull PropertyFinance finance) {
        this.finance = finance;
        rented.setChecked(finance.isRented());
        notRented.setChecked(!finance.isRented());
        endDateOfRent.setText(LongExtKt.toStringWithDisplayFormat(finance.getLeasedToDate()));
        setValue(purchasePriceValue, (int) finance.getPurchasePrice());
        setValue(loanAmountValue, (int) finance.getLoanAmount());
        setValue(estimatedValueValue, (int) finance.getEstimatedValue());
        setValue(weeklyRentValue, (int) finance.getActualRent());
        setValue(estimatedWeeklyRentValue, (int) finance.getEstimatedRent());
        calculateValues();
    }

    private void setValue(EditText editText, int value) {
        if (value != 0) {
            editText.setText(String.valueOf(value));
        } else {
            editText.setText("");
        }
    }

    private void calculateValues() {
        double yield = PropertyCalculator.calculateYield(finance);
        yieldValue.setText(shortFormatYield(getContext(), yield));

        double lvr = PropertyCalculator.calculateLvr(finance);
        lvrValue.setText(shortFormatLvr(getContext(), lvr));

        double valueChange = PropertyCalculator.calculateValueChange(finance);
        valueChangeValue.setText(StringUtils.formatChangedValue(getContext(), valueChange));
    }

    private void initView() {
        inflate(getContext(), R.layout.view_edit_property_investment_summary, this);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(R.color.white));

        yieldValue = ButterKnife.findById(yield, R.id.value);
        lvrValue = ButterKnife.findById(lvr, R.id.value);
        valueChangeValue = ButterKnife.findById(valueChange, R.id.value);

        initInputFields();
        initTextFields();
        initEndDateOfRent();

        rentOptions.setOnCheckedChangeListener((radioGroup, i) ->
        {
            switch (i) {
                case R.id.rented:
                    onRentedClicked();
                    break;
                case R.id.notRented:
                    onNotRentedClicked();
                    break;
            }
        });
    }

    private void onNotRentedClicked() {
        weeklyRent.setVisibility(GONE);
        endOfRent.setVisibility(GONE);
        estimatedWeeklyRent.setVisibility(VISIBLE);
        finance.setRented(false);
        calculateValues();
        notifyOnChangeListener();
    }

    private void onRentedClicked() {
        estimatedWeeklyRent.setVisibility(GONE);
        weeklyRent.setVisibility(VISIBLE);
        endOfRent.setVisibility(VISIBLE);
        finance.setRented(true);
        calculateValues();
        notifyOnChangeListener();
    }

    private void initTextFields() {
        TextView yieldDesc = ButterKnife.findById(yield, R.id.description);
        yieldDesc.setText(R.string.edit_property_yield);
        TextView lvrDesc = ButterKnife.findById(lvr, R.id.description);
        lvrDesc.setText(R.string.edit_property_lvr);
        TextView valueChangeDesc = ButterKnife.findById(valueChange, R.id.description);
        valueChangeDesc.setText(R.string.edit_property_value_change);

        TextView purchasePriceTitle = ButterKnife.findById(purchasePrice, R.id.title);
        purchasePriceTitle.setText(R.string.edit_property_purchase_price);
        TextView loanAmountTitle = ButterKnife.findById(loanAmount, R.id.title);
        loanAmountTitle.setText(R.string.edit_property_loan_amount);
        TextView estimatedValueTitle = ButterKnife.findById(estimatedValue, R.id.title);
        estimatedValueTitle.setText(R.string.edit_property_estimated_value);

        TextView weeklyRentTitle = ButterKnife.findById(weeklyRent, R.id.title);
        weeklyRentTitle.setText(R.string.edit_property_weekly_rent);
        TextView estimatedWeeklyRentTitle = ButterKnife.findById(estimatedWeeklyRent, R.id.title);
        estimatedWeeklyRentTitle.setText(R.string.edit_property_estimated_weekly_rent);
    }

    private void initInputFields() {
        purchasePriceValue = ButterKnife.findById(purchasePrice, R.id.value);
        purchasePriceValue.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                purchasePriceValue.removeTextChangedListener(this);
                String numberFormatted = withCurrency(toFormattedNumberValue(charSequence.toString()));
                purchasePriceValue.setText(numberFormatted);
                purchasePriceValue.setSelection(numberFormatted.length()); //moves the pointer to end
                purchasePriceValue.addTextChangedListener(this);
                finance.setPurchasePrice(toDoubleOrDefault(charSequence.toString(), 0));
                calculateValues();
                notifyOnChangeListener();
            }
        });
        loanAmountValue = ButterKnife.findById(loanAmount, R.id.value);
        loanAmountValue.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loanAmountValue.removeTextChangedListener(this);
                String numberFormatted = withCurrency(toFormattedNumberValue(charSequence.toString()));
                loanAmountValue.setText(numberFormatted);
                loanAmountValue.setSelection(numberFormatted.length()); //moves the pointer to end
                loanAmountValue.addTextChangedListener(this);
                finance.setLoanAmount(toDoubleOrDefault(charSequence.toString(), 0));
                calculateValues();
                notifyOnChangeListener();
            }
        });
        estimatedValueValue = ButterKnife.findById(estimatedValue, R.id.value);
        estimatedValueValue.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                estimatedValueValue.removeTextChangedListener(this);
                String numberFormatted = withCurrency(toFormattedNumberValue(charSequence.toString()));
                estimatedValueValue.setText(numberFormatted);
                estimatedValueValue.setSelection(numberFormatted.length()); //moves the pointer to end
                estimatedValueValue.addTextChangedListener(this);
                finance.setEstimatedValue(toDoubleOrDefault(charSequence.toString(), 0));
                calculateValues();
                notifyOnChangeListener();
            }
        });
        weeklyRentValue = ButterKnife.findById(weeklyRent, R.id.value);
        weeklyRentValue.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                weeklyRentValue.removeTextChangedListener(this);
                String numberFormatted = withCurrency(toFormattedNumberValue(charSequence.toString()));
                weeklyRentValue.setText(numberFormatted);
                weeklyRentValue.setSelection(numberFormatted.length()); //moves the pointer to end
                weeklyRentValue.addTextChangedListener(this);
                finance.setActualRent(toDoubleOrDefault(charSequence.toString(), 0));
                calculateValues();
                notifyOnChangeListener();
            }
        });
        estimatedWeeklyRentValue = ButterKnife.findById(estimatedWeeklyRent, R.id.value);
        estimatedWeeklyRentValue.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                estimatedWeeklyRentValue.removeTextChangedListener(this);
                String numberFormatted = withCurrency(toFormattedNumberValue(charSequence.toString()));
                estimatedWeeklyRentValue.setText(numberFormatted);
                estimatedWeeklyRentValue.setSelection(numberFormatted.length()); //moves the pointer to end
                estimatedWeeklyRentValue.addTextChangedListener(this);
                finance.setEstimatedRent(toDoubleOrDefault(charSequence.toString(), 0));
                calculateValues();
                notifyOnChangeListener();
            }
        });
    }

    private void initEndDateOfRent() {
        endOfRent.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(finance.getLeasedToDate()));
            DatePickerDialog datePicker = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) ->
                    {
                        long endDate = DateUtils.toTimeInMillis(year, monthOfYear, dayOfMonth);
                        finance.setLeasedToDate(endDate);
                        endDateOfRent.setText(LongExtKt.toStringWithDisplayFormat(endDate));
                        notifyOnChangeListener();
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            Activity activity = ((Activity) getContext());
            datePicker.show(activity.getFragmentManager(), TAG_DATE_PICKER_DIALOG);
        });
    }

    private void notifyOnChangeListener() {
        if (listener != null) {
            listener.onPropertyFinanceChanged(finance);
        }
    }

    public interface OnPropertyFinanceChangedListener {
        void onPropertyFinanceChanged(PropertyFinance finance);
    }
}
