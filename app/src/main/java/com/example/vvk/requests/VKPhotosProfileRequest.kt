package com.example.vvk.requests

import com.example.vvk.data.VKPhotoProfile
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKPhotosProfileRequest(id: String) : VKRequest<List<VKPhotoProfile>>("photos.get") {
    init {
        if (id.isNotEmpty()) addParam("owner_id", id)
        addParam("album_id", "profile")
        addParam("rev", 0)
        addParam("extended", 1)
    }

    override fun parse(r: JSONObject): List<VKPhotoProfile> {
        val response = r.getJSONObject("response")
        val array = response.getJSONArray("items")
        val photos = arrayListOf<VKPhotoProfile>()

        for (i : Int in 0 until array.length())
            photos.add(VKPhotoProfile.parse(array.getJSONObject(i)))

        return photos
    }
}