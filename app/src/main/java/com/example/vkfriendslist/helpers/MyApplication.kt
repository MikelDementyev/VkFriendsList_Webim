package com.example.vkfriendslist.helpers

import android.app.Application
import android.preference.PreferenceManager
import com.example.vkfriendslist.R
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk

class MyApplication: Application() {

    private var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val debSl = sharedPreferences.edit()
                debSl.putBoolean(getString(R.string.is_login), false)
                debSl.apply()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(applicationContext)
    }
}