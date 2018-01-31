package com.soho.sohoapp.feature.chat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.adapter.ChatChannelAdapter
import com.soho.sohoapp.feature.chat.chatconversation.ChatConversationActivity
import com.soho.sohoapp.feature.chat.contract.ChatChannelContract
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.chat.presenter.ChatChannelPresenter
import com.soho.sohoapp.feature.home.BaseModel
import com.twilio.chat.Message

/**
 * Created by mariolopez on 16/10/17.
 */
class ChatChannelFragment : Fragment(), ChatChannelContract.ViewInteractable {

    companion object {

        @JvmField
        val TAG: String = "ChatChannelFragment"

        @JvmStatic
        fun newInstance() = ChatChannelFragment()
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipeRefresh) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    lateinit var adapter: ChatChannelAdapter
    lateinit var presenter: ChatChannelContract.ViewPresentable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_chat_channel, container, false)
        ButterKnife.bind(this, view)
        toolbar.title = getString(R.string.message_title)

        adapter = ChatChannelAdapter(mutableListOf(), onChatChannelClicked())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getChatChannelList()
            adapter.refreshDataSet()
        }

        presenter = ChatChannelPresenter(context, this, Dependencies.DEPENDENCIES.userPrefs)
        presenter.startPresenting()
        return view
    }

    override fun showError(throwable: Throwable) {
        view?.let { Snackbar.make(it, throwable.message ?: getString(R.string.error_occurred), Snackbar.LENGTH_SHORT).show() }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun updateChannelList(baseList: BaseModel) {
        adapter.appendToMessageList(baseList)
        adapter.notifyDataSetChanged()
    }

    override fun updateChannelLastMessage(message: Message) {
        adapter.apply {
            updateChannelLastMessage(message)
            notifyDataSetChanged()
        }
    }

    override fun prependChannel(s: ChatChannel) {
        adapter.prependChannel(s)
        adapter.notifyDataSetChanged()
    }

    override fun onChannelUpdated(updatedChannelList: MutableList<BaseModel>) {
        onChannelChanged(updatedChannelList)
    }

    override fun onChannelDeleted(updatedChannelList: MutableList<BaseModel>) = onChannelChanged(updatedChannelList)

    private fun onChannelChanged(updatedChannelList: MutableList<BaseModel>) {
        adapter.apply {
            updateChannelList(updatedChannelList)
            notifyDataSetChanged()
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun onChatChannelClicked(): (String) -> Unit = {
        activity?.startActivity(Intent(activity, ChatConversationActivity::class.java).apply {
            putExtra(ChatConversationActivity.CHAT_CHANNEL_SID_INTENT_EXTRA, it)
        })
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }
}