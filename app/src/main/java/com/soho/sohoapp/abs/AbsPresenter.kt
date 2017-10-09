package com.soho.sohoapp.abs

interface AbsPresenter {
    fun startPresenting(fromConfigChanges: Boolean = false)

    fun stopPresenting()
}