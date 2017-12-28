package com.soho.sohoapp.feature.chat.chatconversation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.chatconversation.adapter.ChatConversationAdapter
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationContract
import com.soho.sohoapp.feature.chat.chatconversation.contract.ChatConversationPresenter
import com.twilio.chat.Message

/**
 * Created by chowii on 22/12/17.
 */


class ChatConversationActivity : AppCompatActivity(), ChatConversationContract.ViewInteractable {

    companion object {
        val CHAT_CHANNEL_SID_INTENT_EXTRA = this::class.java.`package`.name
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    @BindView(R.id.swipeRefresh) lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

    private lateinit var channelSid: String

    private lateinit var presenter: ChatConversationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chat_conversation)
        ButterKnife.bind(this)
        channelSid = intent.extras.getString(CHAT_CHANNEL_SID_INTENT_EXTRA, "")
        presenter = ChatConversationPresenter(this, this, channelSid)
        presenter.startPresenting()
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(findViewById(android.R.id.content), throwable.message ?: "Error occurred", Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun configureAdapter(messageList: List<Message>) {
        recyclerView.adapter = ChatConversationAdapter(messageList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
