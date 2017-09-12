package com.soho.sohoapp

import android.content.Context
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.logger.AndroidLogger
import com.soho.sohoapp.logger.Logger
import com.soho.sohoapp.network.ApiClient
import com.soho.sohoapp.network.SohoService
import com.soho.sohoapp.permission.eventbus.AndroidEventBus
import com.soho.sohoapp.preferences.Prefs

enum class Dependencies {
    DEPENDENCIES;

    lateinit var preferences: Prefs
        private set

    lateinit var imageLoader: ImageLoader
        private set

    lateinit var logger: Logger
        private set
    lateinit var eventBus: AndroidEventBus
        private set

    val sohoService: SohoService = ApiClient.getService()

    fun init(context: Context) {
        preferences = Prefs(context)
        imageLoader = ImageLoader(context)
        logger = AndroidLogger()
        eventBus = AndroidEventBus()
    }
}
