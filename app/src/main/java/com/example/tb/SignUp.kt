package com.example.tb

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_1.*
import java.util.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_sign_up)
        fkgoback.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        tv_sign_up.setOnClickListener {
            uploadUser()

        }
        buttonChose.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    var selectedPhotoUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data!=null){
            selectedPhotoUri=data.data
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            val bitmapDrawable=BitmapDrawable(bitmap)
            buttonChose.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadUser(){
        checking()
        auth.createUserWithEmailAndPassword(tv_email.text.toString(),tv_password.text.toString())
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    saveUser()
                    startActivity(Intent(applicationContext,Nav::class.java))
                }else{
                    Toast.makeText(baseContext,"Sign Up failed",Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun checking(){
        if (!Patterns.EMAIL_ADDRESS.matcher(tv_email.text.toString()).matches()) {
            tv_email.error = "Please enter valid email"
            tv_email.requestFocus()
            return
        }
        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Please enter password"
            tv_password.requestFocus()
            return
        }
        if (tv_confirmPassword.text.toString() != tv_password.text.toString()) {
            tv_confirmPassword.error = "Passwords don't match"
            tv_confirmPassword.requestFocus()
            return
        }
        if (tv_confirmPassword.text.toString().isEmpty()) {
            tv_confirmPassword.error = "Please enter password"
            tv_confirmPassword.requestFocus()
            return
        }

        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Enter Username"
            tv_username.requestFocus()
            return
        }
    }


    private fun saveUser(){
        val email=tv_email.text.toString()
        val username=tv_username.text.toString()
        val ref= FirebaseDatabase.getInstance().getReference("Users" )
        val userId=ref.push().key
        val user=DataUser(userId.toString(),email,username)
        ref.child(userId.toString()).setValue(user)
    }

}
