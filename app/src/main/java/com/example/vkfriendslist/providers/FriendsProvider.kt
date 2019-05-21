package com.example.vkfriendslist.providers

import com.example.vkfriendslist.R
import com.example.vkfriendslist.models.FriendModel
import com.example.vkfriendslist.presenters.FriendsPresenter
import com.google.gson.JsonParser
import com.vk.sdk.api.*

class FriendsProvider(var presenter: FriendsPresenter) {

    fun loadFriendsVk() {
        val request = VKApi.friends().get(VKParameters.from(VKApiConst.COUNT, 5, VKApiConst.FIELDS, "city, country, photo_100, online"))
        request.executeWithListener(object: VKRequest.VKRequestListener(){

            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response?.json.toString()).asJsonObject
                val friendsList = ArrayList<FriendModel>()

                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        null
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }

                    val friend = FriendModel(name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString,
                        city = city,
                        avatar = it.asJsonObject.get("photo_100").asString,
                        isOnline = it.asJsonObject.get("online").asInt == 1)
                    friendsList.add(friend)
                }

                presenter.friendsLoaded(friendsList)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(R.string.error_loading_friends)
            }
        })
    }
}
