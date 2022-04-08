package com.superstore.crsm.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.superstore.crsm.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.login_fragment_container, LoginFragment())
            .commit()
    }
}