package com.soho.sohoapp.feature.chat.chatconversation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.currentUtcDateTimeStamp
import com.soho.sohoapp.feature.chat.chatconversation.adapter.ChatConversationAdapter
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationContract
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationPresenter
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.network.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by chowii on 22/12/17.
 */


class ChatConversationActivity : AppCompatActivity(), ChatConversationContract.ViewInteractable {

    companion object {
        private val packageName = this::class.java.`package`.name

        @JvmField
        val CHAT_CHANNEL_SID_INTENT_EXTRA = packageName + ".chat_channel_sid"

        @JvmField
        val CHAT_CHANNEL_PARTICIPANT_INTENT_EXTRA = packageName + ".chat_channel_participant"

        @JvmStatic
        private val GALLERY_REQUEST_CODE: Int = 121

        @JvmStatic
        private val CAMERA_REQUEST_CODE: Int = 122
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.swipeRefresh) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.message_edit_text) lateinit var messageEditText: EditText

    @OnClick(R.id.attach_button)
    fun onAttachClick() {
        val photoDialog = AddPhotoDialog(this)
        photoDialog.show(object : AddPhotoDialog.OnItemClickedListener {
            override fun onTakeNewPhotoClicked() {
                val cameraPremission = ContextCompat.checkSelfPermission(this@ChatConversationActivity, Manifest.permission.CAMERA)
                if (cameraPremission == PackageManager.PERMISSION_GRANTED) {
                    val s = Intent().apply {
                        action = MediaStore.ACTION_IMAGE_CAPTURE
                        putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Keys.ChatImage.createUri(Date().currentUtcDateTimeStamp(), this@ChatConversationActivity)
                                )
                    }
                    if (s.resolveActivity(packageManager) != null) {
                        startActivityForResult(s, CAMERA_REQUEST_CODE)
                    }
                }
            }

            override fun onChooseFromGalleryClicked() {
                val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                }
                this@ChatConversationActivity.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
            }
        })
    }

    @OnClick(R.id.send_button)
    fun onSendClick() {
        if (messageEditText.text.isNullOrBlank())
            DEPENDENCIES.twilioManager.sendMessageToChannel(channelSid, messageEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { appendMessageToView(ChatMessage(it, null)) },
                            { DEPENDENCIES.logger.e(it.message, it) })
    }

    private fun appendMessageToView(it: ChatMessage) {
        messageEditText.text.clear()
        chatConversationAdapter.appendMessage(it)
        chatConversationAdapter.notifyDataSetChanged()
        recyclerView.smoothScrollToPosition(chatConversationAdapter.itemCount)
    }

    private lateinit var channelSid: String
    private lateinit var participant: String
    private lateinit var presenter: ChatConversationPresenter
    private val chatConversationAdapter = ChatConversationAdapter(mutableListOf(), DEPENDENCIES.userPrefs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        ButterKnife.bind(this)
        swipeRefreshLayout.isEnabled = false
        channelSid = intent.extras.getString(CHAT_CHANNEL_SID_INTENT_EXTRA, "")
        participant = intent.extras.getString(CHAT_CHANNEL_PARTICIPANT_INTENT_EXTRA, "")
        presenter = ChatConversationPresenter(this, this, channelSid)
        presenter.startPresenting()
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = participant
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(findViewById(android.R.id.content), throwable.message ?: "Error occurred", Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun configureAdapter(messageList: MutableList<ChatMessage>) {
        recyclerView.apply {
            chatConversationAdapter.updatedMessageList(messageList)
            adapter = chatConversationAdapter
            layoutManager = LinearLayoutManager(this@ChatConversationActivity, LinearLayoutManager.VERTICAL, false)
            smoothScrollToPosition(chatConversationAdapter.itemCount)
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (resultCode) {
            RESULT_OK -> when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    presenter.uploadGalleryImageFromIntent(data.data)
                }
                CAMERA_REQUEST_CODE -> presenter.uploadGalleryImageFromIntent(data.data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
