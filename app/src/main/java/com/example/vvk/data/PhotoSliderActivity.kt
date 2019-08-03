package com.example.vvk.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.vvk.adapters.PhotosFragmentPagerAdapter
import com.example.vvk.R

class PhotoSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_slider)

        val photos : List<Photo> = intent.getParcelableArrayListExtra("photos")
        val position : Int = intent.getIntExtra("position", 0)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = PhotosFragmentPagerAdapter(supportFragmentManager, photos)
        val tvPosition = findViewById<TextView>(R.id.tv_position)

        tvPosition.text = "1 из ${photos.size}"

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tvPosition.text = "${position + 1} из ${photos.size}"
            }
        })

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        viewPager.adapter = adapter
        viewPager.currentItem = position
    }
}
