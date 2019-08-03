package com.example.vvk.fragments


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vvk.R
import com.example.vvk.adapters.RecyclePhotosProfileAdapter
import com.example.vvk.data.VKPhotoProfile
import com.example.vvk.data.VKProfile
import com.example.vvk.requests.VKPhotosProfileRequest
import com.example.vvk.requests.VKProfileRequest
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import java.lang.Exception

class ProfileFragment : Fragment() {

    private val profilePhotoRequest = "PhotoProfileRequest"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var id = ""
        if (arguments != null) {
            id = arguments!!.getString("id", "")
        }

        VK.execute(VKProfileRequest(id), object : VKApiCallback<VKProfile> {
            override fun fail(error: VKApiExecutionException) {
                Log.d(profilePhotoRequest, error.detailMessage)
            }

            override fun success(result: VKProfile) {
                bindInfo(view, result)
            }
        })

        VK.execute(VKPhotosProfileRequest(id), object  : VKApiCallback<List<VKPhotoProfile>> {
            override fun fail(error: VKApiExecutionException) {
                Log.d(profilePhotoRequest, error.detailMessage)
            }

            override fun success(result: List<VKPhotoProfile>) {
                bindWallPhotos(view, result)
            }
        })

        return view
    }

    private fun bindInfo(view : View, profile : VKProfile) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCountry = view.findViewById<TextView>(R.id.tv_contry)
        val tvCity = view.findViewById<TextView>(R.id.tv_city)
        val tvBirthDate = view.findViewById<TextView>(R.id.tv_bdate)
        val imProfile = view.findViewById<ImageView>(R.id.im_profile)

        val pbInfo = view.findViewById<ProgressBar>(R.id.pb_profile_info)
        val pbProfileImage = view.findViewById<ProgressBar>(R.id.pb_profile_image)
        val profileInfoContainer = view.findViewById<ConstraintLayout>(R.id.profile_info_container)

        tvName.text = profile.name
        tvCountry.text = profile.country
        tvCity.text = profile.homeTown
        tvBirthDate.text = profile.bDate

        pbInfo.visibility = View.INVISIBLE
        profileInfoContainer.visibility = View.VISIBLE

        Picasso.get().load(Uri.parse(profile.profilePhoto))
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(imProfile, object : Callback {
                override fun onSuccess() {
                    pbProfileImage.visibility = View.INVISIBLE
                    imProfile.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun bindWallPhotos(view : View, photos : List<VKPhotoProfile>) {
        val recycleView = view.findViewById<RecyclerView>(R.id.rv_wall_photos)
        val adapter = RecyclePhotosProfileAdapter(activity, photos)

        recycleView.adapter = adapter
    }
}
