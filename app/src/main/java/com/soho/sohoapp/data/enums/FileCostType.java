package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        FileCostType.EXPENSE,
        FileCostType.INCOME
})

@Retention(RetentionPolicy.SOURCE)
public @interface FileCostType {
    String EXPENSE = "expense";
    String INCOME = "income";
}