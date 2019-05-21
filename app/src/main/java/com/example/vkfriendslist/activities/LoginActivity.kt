package com.example.vkfriendslist.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vkfriendslist.R
import com.example.vkfriendslist.presenters.LoginPresenter
import com.example.vkfriendslist.views.LoginView
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        login_button.setOnClickListener {
            if(!sharedPreferences.getBoolean(getString(R.string.is_login), false)) {
                VKSdk.login(this@LoginActivity, VKScope.FRIENDS)
            } else {
                val i = Intent(this, FriendsActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!loginPresenter.loginVk(requestCode, resultCode, data, this)){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        login_button.visibility = View.GONE
        progress_view.visibility = View.VISIBLE
    }

    override fun endLoading() {
        login_button.visibility = View.VISIBLE
        progress_view.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(this, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        val i = Intent(this, FriendsActivity::class.java)
        startActivity(i)
    }
}
