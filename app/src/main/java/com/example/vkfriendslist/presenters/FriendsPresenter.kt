package com.example.vkfriendslist.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vkfriendslist.R
import com.example.vkfriendslist.models.FriendModel
import com.example.vkfriendslist.providers.FriendsProvider
import com.example.vkfriendslist.views.FriendsView


@InjectViewState
class FriendsPresenter: MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(this).loadFriendsVk()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if(friendsList.size == 0){
            viewState.setupEmptyList()
            viewState.showError(R.string.friend_no_item)
        } else {
            viewState.setupFriendsList(friendsList)
        }
    }
    fun showError(textResource: Int){
        viewState.showError(textResource = textResource)
    }
}