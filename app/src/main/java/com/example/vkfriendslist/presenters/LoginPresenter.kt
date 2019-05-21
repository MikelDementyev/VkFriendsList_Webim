package com.example.vkfriendslist.presenters

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vkfriendslist.R
import com.example.vkfriendslist.views.LoginView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError


@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {

    fun loginVk(requestCode: Int, resultCode: Int, data: Intent?, context: Context): Boolean {
        if(!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken>{
                override fun onResult(res: VKAccessToken?) {
                    viewState.openFriends()
                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val debSl = sharedPreferences.edit()
                    debSl.putBoolean(context.getString(R.string.is_login), true)
                    debSl.apply()
                }

                override fun onError(error: VKError?) {
                    viewState.showError(R.string.login_error_credentials)
                }

            })){
            return false
        }
        return true
    }
}