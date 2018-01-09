package com.soho.sohoapp.feature.marketplaceview.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.chat.chatconversation.ChatConversationActivity
import com.soho.sohoapp.network.Keys.PropertyEnquire.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

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

    @OnClick(R.id.submit_enquire_button)
    fun onSubmitClick() {
        val userId = DEPENDENCIES.userPrefs.user?.id ?: 0
        val chatType = if (userId != 0) CHAT_PROPERTY else CHAT_USER
        val conversationMap = hashMapOf(
                RESOURCE_ID to userId,
                CHAT_TYPE to chatType,
                PROPERTY_ID to property.id
        )
        val loading = LoadingDialog(this)
        loading.show("Loading messages")
        DEPENDENCIES.sohoService.getConversation(conversationMap)
                .switchMap { DEPENDENCIES.twilioManager.sendMessageToChannel(it.channelSid, enquiryMessage()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            startActivity(Intent(this, ChatConversationActivity::class.java).apply {
                                putExtra(ChatConversationActivity.CHAT_CHANNEL_SID_INTENT_EXTRA, it.channelSid)
                            })
                            loading.dismiss()
                            Snackbar.make(findViewById(android.R.id.content), R.string.error_occurred, Snackbar.LENGTH_SHORT).show()
                        },
                        {
                            Snackbar.make(findViewById(android.R.id.content), R.string.error_occurred, Snackbar.LENGTH_SHORT).show()
                            Log.d("LOG_TAG---", "onSubmitClick error: " + it.message)
                            loading.dismiss()
                        }
                )
    }

    private fun enquiryMessage() = String.format(
            Locale.getDefault(),
            getString(R.string.enquiry_message_format),
            getString(R.string.enquiry_interested_in),
            when {
                priceCheckBox.isChecked -> priceCheckBox.text
                contractCheckBox.isChecked -> contractCheckBox.text
                inspectCheckBox.isChecked -> inspectCheckBox.text
                contactForSimilarCheckBox.isChecked -> contactForSimilarCheckBox.text
                otherCheckBox.isChecked -> otherCheckBox.text
                else -> ""
            },
            commentsEditText.text.toString()
    )

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
        toolbar.title = getString(R.string.enquire_title)
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