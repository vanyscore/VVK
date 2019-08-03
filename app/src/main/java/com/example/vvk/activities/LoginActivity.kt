package com.example.vvk.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.vvk.R
import com.vk.api.sdk.*
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("Fingerprint", VKUtils.getCertificateFingerprint(this, packageName)!![0])
        VK.initialize(this)

        VK.addTokenExpiredHandler(object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                VK.logout()
            }
        })

        findViewById<Button>(R.id.btn_log_in).setOnClickListener {
            if (!VK.isLoggedIn()) {
                VK.login(this, hashSetOf(VKScope.FRIENDS, VKScope.EMAIL))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        VK.onActivityResult(requestCode, resultCode, data, object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Toast.makeText(this@LoginActivity, token.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(this@LoginActivity, "Error: $errorCode", Toast.LENGTH_SHORT).show()
            }
        })

        super.onActivityResult(requestCode, resultCode, data)
    }
}
