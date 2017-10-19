package com.soho.sohoapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.PickerItem

class TypePicker : LinearLayout {
    @BindView(R.id.icon)
    lateinit var icon: ImageView

    @BindView(R.id.text)
    lateinit var text: TextView

    @BindView(R.id.to_left)
    lateinit var toLeft: ImageView

    @BindView(R.id.to_right)
    lateinit var toRight: ImageView

    private val pickerItems = mutableListOf<PickerItem>()
    private var currentItem = 0
    private var listener: OnItemChangedListener? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    fun setPropertyTypes(propertyTypes: List<PickerItem>, currentItem: Int = 0) {
        pickerItems.clear()
        pickerItems.addAll(propertyTypes)
        this.currentItem = currentItem

        initItem()
    }

    fun setListener(listener: OnItemChangedListener) {
        this.listener = listener
    }

    fun disable() {
        icon.isEnabled = false
        toLeft.isEnabled = false
        toRight.isEnabled = false
        text.setTextColor(resources.getColor(R.color.disabledText))
        setBackgroundColor(resources.getColor(R.color.disabledBackground))
    }

    @OnClick(R.id.to_left)
    fun toLeft() {
        currentItem -= 1
        validateValue()
        validateButtons()

        initItem()
        listener?.onItemChanged(pickerItems[currentItem])
    }

    @OnClick(R.id.to_right)
    fun toRight() {
        currentItem += 1
        validateValue()
        validateButtons()

        initItem()
        listener?.onItemChanged(pickerItems[currentItem])
    }

    private fun initView() {
        inflate(context, R.layout.view_type_picker, this)
        ButterKnife.bind(this)
        orientation = HORIZONTAL
        gravity = View.TEXT_ALIGNMENT_CENTER
    }

    private fun validateValue() {
        if (currentItem < 0) {
            currentItem = 0
        } else if (currentItem >= pickerItems.size) {
            currentItem = pickerItems.size - 1
        }
    }

    private fun validateButtons() {
        toLeft.isEnabled = currentItem >= 0
        toRight.isEnabled = currentItem < pickerItems.size
    }

    private fun initItem() {
        text.text = pickerItems[currentItem].displayableString

        val iconRes = pickerItems[currentItem].icon
        if (iconRes != null) {
            icon.visibility = View.VISIBLE
            icon.setImageResource(iconRes)
        } else {
            icon.visibility = View.GONE
        }
    }

    interface OnItemChangedListener {
        fun onItemChanged(pickerItem: PickerItem)
    }
}