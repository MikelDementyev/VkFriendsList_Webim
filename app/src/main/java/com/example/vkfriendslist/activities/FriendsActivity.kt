package com.example.vkfriendslist.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vkfriendslist.R
import com.example.vkfriendslist.adapters.FriendsAdapter
import com.example.vkfriendslist.models.FriendModel
import com.example.vkfriendslist.presenters.FriendsPresenter
import com.example.vkfriendslist.views.FriendsView
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : MvpAppCompatActivity(), FriendsView {

    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private lateinit var mAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        friendsPresenter.loadFriends()
        mAdapter = FriendsAdapter()

        recycler_friends.adapter = mAdapter
        recycler_friends.layoutManager = LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        recycler_friends.setHasFixedSize(true)
    }

    override fun showError(textResource: Int) {
        error_no_friends.text = getString(textResource)
    }

    override fun setupEmptyList() {
        recycler_friends.visibility = View.GONE
        error_no_friends.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendsList: ArrayList<FriendModel>) {
        recycler_friends.visibility = View.VISIBLE
        error_no_friends.visibility = View.GONE

        mAdapter.setupFriends(friendsList)
    }

    override fun startLoading() {
        progress_view_friends.visibility = View.VISIBLE
        error_no_friends.visibility = View.GONE
        recycler_friends.visibility = View.GONE
    }

    override fun endLoading() {
        progress_view_friends.visibility = View.GONE
    }
}
