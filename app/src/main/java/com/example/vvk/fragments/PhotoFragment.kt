package com.example.vvk.fragments


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vvk.R
import com.example.vvk.data.Photo
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        val photo : Photo? = arguments!!.getParcelable("photo")
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val tvLikes = view.findViewById<TextView>(R.id.tv_likes)

        tvLikes.text = "${photo!!.likes} лайков"
        Picasso.get().load(Uri.parse(photo!!.url))
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.INVISIBLE
                    imageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            })

        return view
    }

    companion object {
        fun newInstance(photo : Photo) : PhotoFragment {
            val bundle = Bundle()
            bundle.putParcelable("photo", photo)
            val fragment = PhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
