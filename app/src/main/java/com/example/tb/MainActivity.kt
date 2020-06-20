package com.example.tb

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth=FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val constrainLayout=findViewById<RelativeLayout>(R.id.layout)
        val frameAnimation: AnimationDrawable =constrainLayout.background as AnimationDrawable
        frameAnimation.setEnterFadeDuration(800)
        frameAnimation.setExitFadeDuration(800)
        frameAnimation.start()
        tv_log_in.setOnClickListener {
            startActivity(Intent(applicationContext, SignIn::class.java))
            finish()
        }
        tv_sign_up.setOnClickListener {
            startActivity(Intent(applicationContext, SignUp::class.java))
            finish()
        }

    }
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        } else {

        }
    }
}
