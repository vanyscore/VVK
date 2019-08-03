package com.example.vvk.requests

import com.example.vvk.data.VKFriend
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKFriendsRequest : VKRequest<List<VKFriend>>("friends.get") {
    init {
        addParam("order", "random")
        addParam("fields", "photo_100,education")
    }

    override fun parse(r: JSONObject): List<VKFriend> {
        val friends = arrayListOf<VKFriend>()

        val response = r.getJSONObject("response")
        val items = response.getJSONArray("items")

        for (i : Int in 0 until items.length())
            friends.add(VKFriend.parse(items.getJSONObject(i)))

        return friends
    }
}