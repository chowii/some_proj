package com.soho.sohoapp.utils;

import io.reactivex.annotations.NonNull;

public class Optional<T> {

    private T value;

    private Optional() {
        this.value = null;
    }

    private Optional(@NonNull T value) {
        this.value = value;
    }

    public static <T> Optional<T> empty() {
        return new Optional<>();
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return value;
    }
}
