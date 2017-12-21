package com.soho.sohoapp.feature.marketplaceview.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Property

/**
 * Created by chowii on 21/12/17.
 */

class EnquireActivity : AppCompatActivity() {

    companion object {
        @JvmField
        val PROPERTY_ENQUIRY_INTENT_EXTRA = this::class.java.`package`.name + ".property"
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.price_check_box) lateinit var priceCheckBox: CheckBox
    @BindView(R.id.contract_check_box) lateinit var contractCheckBox: CheckBox
    @BindView(R.id.inspect_check_box) lateinit var inspectCheckBox: CheckBox
    @BindView(R.id.contact_for_similar_check_box) lateinit var contactForSimilarCheckBox: CheckBox
    @BindView(R.id.other_check_box) lateinit var otherCheckBox: CheckBox
    @BindView(R.id.comments_edit_text) lateinit var commentsEditText: EditText
    @BindView(R.id.submit_enquire_button) lateinit var submitButton: Button

    @OnClick(R.id.submit_enquire_button)
    fun onSubmitClick() {
        //TODO Go to or create chat channel
    }

    private lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquire)
        ButterKnife.bind(this)
        configureToolbar()

        property = intent.extras[PROPERTY_ENQUIRY_INTENT_EXTRA] as Property

        priceCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(isChecked, false, false, false, false, false)
        }
        contractCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(false, isChecked, false, false, false, false)
        }
        inspectCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(false, false, isChecked, false, false, false)
        }
        contractCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(false, false, false, isChecked, false, false)
        }
        contactForSimilarCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(false, false, false, false, isChecked, false)
        }
        otherCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toggleCheckedState(false, false, false, false, false, isChecked)
        }

    }

    private fun configureToolbar() {
        toolbar.title = "Enquire"
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun toggleCheckedState(
            priceCheck: Boolean,
            contractCheck: Boolean,
            inspectCheck: Boolean,
            contactCheck: Boolean,
            similarCheck: Boolean,
            otherCheck: Boolean
    ) {
        priceCheckBox.isChecked = priceCheck
        contractCheckBox.isChecked = contractCheck
        inspectCheckBox.isChecked = inspectCheck
        contractCheckBox.isChecked = contactCheck
        contactForSimilarCheckBox.isChecked = similarCheck
        otherCheckBox.isChecked = otherCheck
    }
}