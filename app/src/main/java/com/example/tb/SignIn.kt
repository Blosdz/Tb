package com.example.tb

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.fkgoback
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignIn : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        fkgoback.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
        tv_log_in.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (!Patterns.EMAIL_ADDRESS.matcher(tv_emailIn.text.toString()).matches()) {
            tv_emailIn.error = "Please enter valid email"
            tv_emailIn.requestFocus()
            return
        }

        if (tv_passwordIn.text.toString().isEmpty()) {
            tv_passwordIn.error = "Please enter password"
            tv_passwordIn.requestFocus()
            return
        }

        if (tv_emailIn.text.toString().isEmpty()){
            tv_emailIn.error="please enter email"
            tv_emailIn.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(tv_emailIn.text.toString(), tv_passwordIn.text.toString())
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
                updateUI(user)

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

            // ...
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {1
        if(currentUser!=null){
            startActivity(Intent(applicationContext,Nav::class.java))
        }
    }

}
