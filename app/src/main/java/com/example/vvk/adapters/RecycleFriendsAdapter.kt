package com.example.vvk.adapters

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vvk.fragments.ProfileFragment
import com.example.vvk.R
import com.example.vvk.data.VKFriend
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class RecycleFriendsAdapter(private val context: Context?,
                            private val friends : List<VKFriend>,
                            private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<RecycleFriendsAdapter.FriendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_friend, parent, false))
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindInfo(friends[position])
    }

    inner class FriendViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val progressBar : ProgressBar = view.findViewById(R.id.pb_friend)
        private val image : ImageView = view.findViewById(R.id.im_friend)
        private val tvName : TextView = view.findViewById(R.id.tv_name)
        private val tvInstitute : TextView = view.findViewById(R.id.tv_institute)
        private val tvOnline : TextView = view.findViewById(R.id.tvOnline)
        private val cardView : CardView =  view.findViewById(R.id.cardView)

        fun bindInfo(friend : VKFriend) {
            changeVisibility(false)
            Picasso.get().load(Uri.parse(friend.photoUrl))
                .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .into(image, object : Callback {
                    override fun onError(e: Exception?) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess() {
                        tvName.text = friend.name
                        tvInstitute.text = friend.institute

                        val isOnline = friend.isOnline
                        if (isOnline) {
                            tvOnline.setTextColor(context!!.resources.getColor(R.color.colorGreen))
                            tvOnline.text = "Онлайн"
                        } else {
                            tvOnline.setTextColor(context!!.resources.getColor(R.color.colorRed))
                            tvOnline.text = "Не в сети"
                        }

                        changeVisibility(true)

                        itemView.setOnClickListener(this@FriendViewHolder)
                    }
                })
        }

        private fun changeVisibility(mustShow : Boolean) {
            if (mustShow) {
                progressBar.visibility = View.INVISIBLE
                cardView.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                tvInstitute.visibility = View.VISIBLE
                tvOnline.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.VISIBLE
                cardView.visibility = View.INVISIBLE
                tvName.visibility = View.INVISIBLE
                tvInstitute.visibility = View.INVISIBLE
                tvOnline.visibility = View.INVISIBLE
            }
        }

        override fun onClick(v: View?) {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString("id", friends[adapterPosition].id)
            fragment.arguments = bundle
            fragmentManager.beginTransaction().replace(R.id.ln_fragment, fragment).commit()
        }
    }
}