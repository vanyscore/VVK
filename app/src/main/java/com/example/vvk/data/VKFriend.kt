package com.example.vvk.data

import org.json.JSONObject

class VKFriend private constructor(val id : String,
               val name : String,
               val institute : String,
               val isOnline : Boolean,
               val photoUrl : String) {
    companion object {
        fun parse(response : JSONObject) : VKFriend {
            val id = response.getString("id")
            val firstName = response.getString("first_name")
            val lastName = response.getString("last_name")
            val institute = if (response.has("university_name")) response.getString("university_name") else ""
            val photoUrl = response.getString("photo_100")
            val isOnline = response.getInt("online") != 0

            return VKFriend(id, "$firstName $lastName", institute, isOnline, photoUrl)
        }
    }
}