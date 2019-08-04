package com.example.vvk.fragments


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
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
                loadPhoto(view, result.photoUrl)
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
        val pbInfo = view.findViewById<ProgressBar>(R.id.pb_profile_info)
        val profileInfoContainer = view.findViewById<LinearLayout>(R.id.profile_info_container)

        val tvOnline = view.findViewById<TextView>(R.id.tv_online)
        if (profile.online == 0) {
            tvOnline.setTextColor(activity!!.resources.getColor(R.color.colorRed))
            tvOnline.text = "Не в сети"
        } else {
            tvOnline.setTextColor(activity!!.resources.getColor(R.color.colorGreen))
            tvOnline.text = "В сети"
        }

        view.findViewById<TextView>(R.id.tv_name).text = profile.name

        if (profile.country.isNotEmpty()) {
            view.findViewById<LinearLayout>(R.id.ll_country).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_contry).text = profile.country
        }

        if (profile.city.isNotEmpty()) {
            view.findViewById<LinearLayout>(R.id.ll_city).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_city).text = profile.city
        }

        if (profile.birthday.isNotEmpty()) {
            view.findViewById<LinearLayout>(R.id.ll_birthday).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_bdate).text = profile.birthday
        }

        if (profile.institute.isNotEmpty()) {
            view.findViewById<LinearLayout>(R.id.ll_institute).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_institute).text = profile.institute
        }

        if (profile.status.isNotEmpty()) {
            view.findViewById<CardView>(R.id.cv_status).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tv_status).text = profile.status
        }

        pbInfo.visibility = View.INVISIBLE
        profileInfoContainer.visibility = View.VISIBLE
    }

    private fun loadPhoto(view : View, url : String) {
        val image : ImageView = view.findViewById(R.id.im_profile)
        val progressBar : ProgressBar = view.findViewById(R.id.pb_im_profile)

        Picasso.get().load(Uri.parse(url))
            .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
            .into(image, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.INVISIBLE
                    image.visibility = View.VISIBLE
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
