package com.example.vvk.data

import org.json.JSONObject

class VKPhotoProfile(val urlP : String, val urlMaxSize : String, val likes : Int) {
    companion object {
        fun parse(photo : JSONObject) : VKPhotoProfile {
            val sizes = photo.getJSONArray("sizes")
            val urlP = sizes.getJSONObject(2).getString("url")
            val urlMaxSize = sizes.getJSONObject(sizes.length() - 1).getString("url")
            val likes = photo.getJSONObject("likes").getInt("count")

            return VKPhotoProfile(urlP, urlMaxSize, likes)
        }
    }
}