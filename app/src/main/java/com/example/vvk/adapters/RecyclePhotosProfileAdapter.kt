package com.example.vvk.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vvk.R
import com.example.vvk.data.Photo
import com.example.vvk.data.PhotoSliderActivity
import com.example.vvk.data.VKPhotoProfile
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class RecyclePhotosProfileAdapter(private val context : Context?,
                                  private val photos : List<VKPhotoProfile>)
    : RecyclerView.Adapter<RecyclePhotosProfileAdapter.ProfilePhotoViewHolder>() {

    private var sliderPhotos : ArrayList<Photo> = arrayListOf()

    init {
        for (photo in photos)
            sliderPhotos.add(Photo(photo.urlMaxSize, photo.likes))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rcv_profile_photo, parent, false)
        return ProfilePhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ProfilePhotoViewHolder, position: Int) {
        holder.loadImage(position)
    }

    inner class ProfilePhotoViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val imPhoto : ImageView = view.findViewById(R.id.im_profile_photo)
        val pb : ProgressBar = view.findViewById(R.id.pb_profile_photo)

        init {
            view.setOnClickListener(this)
        }

        fun loadImage(index : Int) {
            Picasso.get().load(Uri.parse(photos[index].urlP))
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imPhoto, object : Callback {
                    override fun onError(e: Exception?) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess() {
                        pb.visibility = View.INVISIBLE
                        imPhoto.visibility = View.VISIBLE
                    }
                })
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, PhotoSliderActivity::class.java)
            intent.putParcelableArrayListExtra("photos", sliderPhotos)
            intent.putExtra("position", adapterPosition)
            context!!.startActivity(intent)
        }
    }
}