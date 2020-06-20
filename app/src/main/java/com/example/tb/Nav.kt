package com.example.tb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_nav.*

class Nav : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(bottom_navigation, navController)

        auth=FirebaseAuth.getInstance()


    }
    override fun onStart(){
        super.onStart()
        val currentUser=FirebaseAuth.getInstance().currentUser
        if(currentUser==null){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }else{

        }
    }
}
