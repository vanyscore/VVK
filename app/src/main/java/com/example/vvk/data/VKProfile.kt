package com.example.vvk.data

import org.json.JSONObject
import java.util.*

class VKProfile private constructor(val id : String,
                                    val firstName : String, val lastName : String,
                                    val birthday : String,
                                    val country : String,
                                    val city : String,
                                    val institute : String,
                                    val online : Int,
                                    val status : String,
                                    val photoUrl : String) {
    val name = "$firstName $lastName"

    companion object {
        fun parse(response : JSONObject) : VKProfile {
            val id = response.getString("id")
            val firstName = response.getString("first_name")
            val lastName = response.getString("last_name")

            val birthday = if (response.has("bdate")) transformDate(response.getString("bdate")) else ""
            val city = if (response.has("home_town")) response.getString("home_town") else ""
            val country = if (response.has("country")) response.getString("country") else ""
            val institute = if (response.has("university_name")) response.getString("university_name") else ""
            val status = if (response.has("status")) response.getString("status") else ""

            val online = response.getInt("online")
            val profilePhoto = response.getString("photo_max_orig")

            return VKProfile(id, firstName, lastName, birthday, country, city, institute, online, status, profilePhoto)
        }

        private fun transformDate(date : String) : String {
            val parts = arrayListOf<String>()
            for (part in date.split("."))
                parts.add(part.padStart(2, '0'))
            var result = ""
            for (i : Int in 0 until parts.size - 1)
                result += "${parts[i]}."
            result += parts[parts.size - 1]

            return result
        }
    }
}