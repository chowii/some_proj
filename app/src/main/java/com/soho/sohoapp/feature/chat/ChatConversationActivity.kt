package com.soho.sohoapp.feature.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R

/**
 * Created by chowii on 22/12/17.
 */
class ChatConversationActivity : AppCompatActivity() {

    companion object {
        val CHAT_CHANNEL_SID_INTENT_EXTRA = this::class.java.`package`.name
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chat_conversation)
        ButterKnife.bind(this)
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}