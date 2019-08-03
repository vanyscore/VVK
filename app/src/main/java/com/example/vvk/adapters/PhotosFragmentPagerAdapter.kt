package com.example.vvk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.vvk.fragments.PhotoFragment
import com.example.vvk.data.Photo

class PhotosFragmentPagerAdapter(
    fragmentManager : FragmentManager,
    private val photos : List<Photo>)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PhotoFragment.newInstance(photos[position])
    }

    override fun getCount(): Int {
        return photos.size
    }
}