package com.soho.sohoapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Jovan on 11/10/17.
 */

fun EditText.afterTextChanged(afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    afterTextChanged.invoke(editable)
                }
            })
}