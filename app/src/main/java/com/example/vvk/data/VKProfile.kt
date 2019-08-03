package com.example.vvk.data

import org.json.JSONObject

class VKProfile private constructor(val id : String,
                     val firstName : String, val lastName : String,
                     val bDate : String,
                     val country : String?, val homeTown : String?,
                     val profilePhoto : String,
                     val institute : String) {
    val name = "$firstName $lastName"

    companion object {
        fun parse(response : JSONObject) : VKProfile {
            val id = response.getString("id")
            val firstName = response.getString("first_name")
            val lastName = response.getString("last_name")
            val bDate = if (response.has("bdate")) response.getString("bdate") else "Undefined"
            val homeTown = if (response.has("home_town")) response.getString("home_town") else "Undefined"
            val profilePhoto = response.getString("photo_max_orig")
            val country = if (response.has("country")) response.getString("country") else "Russia"
            val institute = if (response.has("university_name")) response.getString("university_name") else "Undefined"

            return VKProfile(id, firstName, lastName, bDate, country, homeTown, profilePhoto, institute)
        }
    }
}