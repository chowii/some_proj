package com.soho.sohoapp

import android.arch.persistence.room.Room
import android.content.Context
import com.soho.sohoapp.database.SohoDatabase
import com.soho.sohoapp.feature.chat.TwilioChatManager
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.logger.AndroidLogger
import com.soho.sohoapp.logger.Logger
import com.soho.sohoapp.network.ApiClient
import com.soho.sohoapp.network.Keys.Database.DB_NAME
import com.soho.sohoapp.network.SohoService
import com.soho.sohoapp.permission.eventbus.AndroidEventBus
import com.soho.sohoapp.preferences.UserPrefs

enum class Dependencies {
    DEPENDENCIES;

    lateinit var userPrefs: UserPrefs
        private set

    lateinit var imageLoader: ImageLoader
        private set

    lateinit var logger: Logger
        private set

    lateinit var eventBus: AndroidEventBus
        private set

    lateinit var database: SohoDatabase
        private set

    val twilioManager: TwilioChatManager = TwilioChatManager()

    val sohoService: SohoService = ApiClient.getService()

    fun init(context: Context) {
        userPrefs = UserPrefs(context)
        imageLoader = ImageLoader(context)
        logger = AndroidLogger()
        eventBus = AndroidEventBus()
        database = Room.databaseBuilder(context, SohoDatabase::class.java, DB_NAME)
                .addMigrations(SohoDatabase.migration1to2)
                .build()
        if (userPrefs.twilioToken.isNotBlank())
            twilioManager.initChatClient(context, userPrefs)
    }
}
