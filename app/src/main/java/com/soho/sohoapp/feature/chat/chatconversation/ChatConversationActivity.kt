package com.soho.sohoapp.feature.chat.chatconversation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.marchinram.rxgallery.RxGallery
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.chatconversation.adapter.ChatConversationAdapter
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationContract
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationPresenter
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.feature.chat.model.PendingMessage
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.DateUtils
import com.soho.sohoapp.utils.TextWatcherAdapter
import com.squareup.picasso.Picasso
import com.twilio.chat.Member
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*


/**
 * Created by chowii on 22/12/17.
 */


class ChatConversationActivity : AppCompatActivity(), ChatConversationContract.ViewInteractable {

    companion object {
        private val packageName = this::class.java.`package`.name

        @JvmField
        val CHAT_CHANNEL_SID_INTENT_EXTRA = packageName + ".chat_channel_sid"

    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.swipeRefresh) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.message_edit_text) lateinit var messageEditText: EditText
    @BindView(R.id.participant_name) lateinit var participantName: TextView
    @BindView(R.id.typing_indicator) lateinit var typeIndicator: TextView
    @BindView(R.id.user_avatar_iv) lateinit var userAvatarImageView: ImageView

    @OnClick(R.id.attach_button)
    fun onAttachClick() {
        val photoDialog = AddPhotoDialog(this)
        photoDialog.show(object : AddPhotoDialog.OnItemClickedListener {
            override fun onTakeNewPhotoClicked() = presenter.takeImageWithCamera()

            override fun onChooseFromGalleryClicked() = presenter.pickImageFromGallery()
        })
    }

    @OnClick(R.id.send_button)
    fun onSendClick() {
        if (messageEditText.text.isNotBlank())
            DEPENDENCIES.twilioManager.sendMessageToChannel(channelSid, messageEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { messageEditText.text.clear() },
                            { DEPENDENCIES.logger.e(it.message, it) })
    }

    private lateinit var channelSid: String
    private lateinit var presenter: ChatConversationPresenter
    private var chatConversationAdapter = ChatConversationAdapter(
            userPrefs = DEPENDENCIES.userPrefs,
            onRetryClick = retryImageUpload(),
            onTopListener = onTopListener()
    )

    private var camera: CameraPicker? = null
    private var gallery: GalleryPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        ButterKnife.bind(this)
        swipeRefreshLayout.isEnabled = false
        channelSid = intent.extras.getString(CHAT_CHANNEL_SID_INTENT_EXTRA, "")
        presenter = ChatConversationPresenter(
                this,
                this,
                channelSid,
                RxGallery.photoCapture(this),
                DEPENDENCIES.twilioManager,
                PermissionManagerImpl.newInstance(this),
                DEPENDENCIES.userPrefs
        )
        presenter.startPresenting()
        configureToolbar()
        configureMessageEditText()
    }

    private fun configureMessageEditText() {
        messageEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                presenter.startedTyping()
                super.onTextChanged(charSequence, i, i1, i2)
            }
        })
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun configureToolbarTitle(title: String) {
        participantName.text = title
    }

    override fun showAvatar(url: String?) {
        url?.let {
            Picasso.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_gray)
                    .into(userAvatarImageView)
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(
                findViewById(android.R.id.content),
                throwable.message ?: getString(R.string.error_occurred),
                Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun prependMessageList(messageList: MutableList<out BaseModel>) {
        chatConversationAdapter.apply {
            prependMessageListToAdapter(messageList)
            notifyDataSetChanged()
        }
    }

    override fun configureAdapter(messageList: MutableList<out BaseModel>) {
        recyclerView.apply {
            chatConversationAdapter.updatedMessageList(messageList)
            adapter = chatConversationAdapter
            val linearLayoutManager = LinearLayoutManager(this@ChatConversationActivity, LinearLayoutManager.VERTICAL, false)
            linearLayoutManager.stackFromEnd = true
            layoutManager = linearLayoutManager
            smoothScrollToPosition(chatConversationAdapter.itemCount)
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun pickImage() {
        gallery = GalleryPicker(this@ChatConversationActivity)
        gallery?.choosePhoto { uploadImage(it) }
    }

    override fun captureImage() {
        camera = CameraPicker(this@ChatConversationActivity)
        camera?.takePhoto { uploadImage(Uri.fromFile(File(it))) }
    }

    private fun uploadImage(uri: Uri) {
        val fileName = String.format(
                Locale.getDefault(),
                getString(R.string.photo_filename_format),
                DateUtils.getDateFormatForFileName())
        val imageFile = Pair(uri, fileName)
        showPendingImage(imageFile)
        presenter.uploadGalleryImageFromIntent(uri, fileName)
    }

    private fun showPendingImage(imageFile: Pair<Uri, String>) {
        chatConversationAdapter.apply {
            appendMessage(PendingMessage(imageFile, true))
            notifyDataSetChanged()
            recyclerView.scrollToPosition(itemCount - 1)
        }
    }

    override fun notifyUploadFailed(filename: Pair<Uri, String>) {
        chatConversationAdapter.apply {
            notifyUploadFailed(filename)
            notifyDataSetChanged()
        }
    }

    override fun updateImageMessage(message: ChatMessage) = chatConversationAdapter.let {
        it.updateImageMessage(message)
        it.notifyDataSetChanged()
        recyclerView.scrollToPosition(it.itemCount - 1)
    }

    override fun appendMessage(message: ChatMessage) = chatConversationAdapter.let {
        it.appendMessage(message)
        it.notifyDataSetChanged()
        recyclerView.scrollToPosition(it.itemCount - 1)
    }

    override fun typingStarted(member: Member) = typeIndicator.let {
        it.visibility = VISIBLE
    }

    override fun typingEnded(member: Member) = typeIndicator.let {
        it.visibility = GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        camera?.onActivityResult(requestCode, resultCode)
        gallery?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun retryImageUpload(): (Pair<Uri, String>) -> Unit = {
        presenter.uploadGalleryImageFromIntent(it.first, it.second)
    }

    private fun onTopListener() :(ChatMessage) -> Unit = {
        presenter.getMessageBefore(it.message)
    }
}
