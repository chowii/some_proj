package com.soho.sohoapp.customviews

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R

/**
 * Created by Jovan on 9/10/17.
 */

class LabeledValueField(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    @BindView(R.id.title)
    lateinit var txtTitle: TextView

    @BindView(R.id.value)
    lateinit var etValue: EditText

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_labeled_value_field, this, true)
        ButterKnife.bind(this)
        configureAttributes(context, attrs)
    }

    private fun configureAttributes(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs,
                R.styleable.LabeledValueField)
        val title = attributes.getString(R.styleable.LabeledValueField_title) ?: ""
        val value = attributes.getString(R.styleable.LabeledValueField_value) ?: ""
        val placeholder = attributes.getString(R.styleable.LabeledValueField_hint) ?: ""
        val inputType = attributes.getInt(R.styleable.LabeledValueField_inputType, InputType.TYPE_CLASS_TEXT)
        txtTitle.text = title
        etValue.setText(value)
        etValue.hint = placeholder
        etValue.inputType = inputType
        attributes.recycle()
    }
}