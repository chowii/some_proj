package com.soho.sohoapp.feature.home;

/**
 * Created by chowii on 14/8/17.
 */

public abstract class BaseFormModel<T> implements BaseModel{

    protected T value;

    protected abstract T getModelValue();
    protected abstract void setModelValue(T value);

}
