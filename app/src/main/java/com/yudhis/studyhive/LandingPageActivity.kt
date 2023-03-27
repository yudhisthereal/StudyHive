package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yudhis.studyhive.databinding.ActivityLandingPageBinding

class LandingPageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLandingPageBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user : FirebaseUser? = auth.currentUser

        binding.txtUsername.text = user?.displayName

        binding.btnLogout.setOnClickListener {
            val gsc: GoogleSignInClient = GoogleSignIn.getClient(
                this@LandingPageActivity,
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )
            gsc.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseAuth.getInstance().signOut()
                    finish()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }
}