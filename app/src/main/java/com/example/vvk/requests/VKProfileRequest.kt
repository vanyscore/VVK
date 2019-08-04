package com.example.vvk.requests

import com.example.vvk.data.VKProfile
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKProfileRequest(id : String) : VKRequest<VKProfile>("users.get") {
    init {
        if (id.isNotEmpty()) addParam("user_ids", id)
        addParam("fields", arrayListOf("contry", "bdate", "home_town", "photo_max_orig", "education", "online", "status").joinToString(","))
    }

    override fun parse(r: JSONObject): VKProfile {
        return VKProfile.parse(r.getJSONArray("response").getJSONObject(0))
    }
}