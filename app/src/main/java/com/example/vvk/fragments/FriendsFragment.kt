package com.example.vvk.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vvk.R
import com.example.vvk.adapters.RecycleFriendsAdapter
import com.example.vvk.data.VKFriend
import com.example.vvk.requests.VKFriendsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException

class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        VK.execute(VKFriendsRequest(), object : VKApiCallback<List<VKFriend>> {
            override fun fail(error: VKApiExecutionException) {
                Log.d("FriendsRequest", error.detailMessage)
            }

            override fun success(result: List<VKFriend>) {
                view.findViewById<ProgressBar>(R.id.pb_friends).visibility = View.INVISIBLE
                bindRecyclerView(view, result)
            }

        })

        return view
    }

    private fun bindRecyclerView(view : View, friends : List<VKFriend>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_friends)
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = RecycleFriendsAdapter(activity, friends, fragmentManager!!)

        recyclerView.adapter = adapter
    }
}
