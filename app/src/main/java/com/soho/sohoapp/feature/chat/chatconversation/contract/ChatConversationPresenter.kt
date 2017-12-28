package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.content.Context
import android.util.Log
import com.soho.sohoapp.feature.chat.TwilioChatManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 22/12/17.
 */
class ChatConversationPresenter(private val context: Context?,
                                private val view: ChatConversationContract.ViewInteractable,
                                private val conversationId: String) : ChatConversationContract.ViewPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val numberOfLastMessages = 35

    override fun startPresenting() {
        view.showLoading()
        getChatConversation()
    }

    override fun getChatConversation() {
        TwilioChatManager.getChatConversation(conversationId)
                .switchMap {
                    TwilioChatManager.getLastMessageList(numberOfLastMessages)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.configureAdapter(it)
                            view.hideLoading()
                        },
                        {
                            Log.d("LOG_TAG---", "Twilio error: " + it?.message)
                            view.showError(Throwable("Access Token expired"))
                            view.hideLoading()
                        }
                )
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}