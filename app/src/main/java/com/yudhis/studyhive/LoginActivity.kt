package com.yudhis.studyhive

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var fBaseAuth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_login)
        setContentView(binding.root)
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("421961381611-c33pgbcqcjuvi3r01aqjors21ogsd8rh.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val gsc : GoogleSignInClient = GoogleSignIn.getClient(this, gso)
//        val txtSignup : TextView = findViewById(R.id.txt_signup)
//        val txtForgotPass : TextView = findViewById(R.id.txt_forgot_pass)
//        val btnGoogleLogin : Button = findViewById(R.id.btn_google_login)
        binding.btnGoogleLogin.setOnClickListener{
            googleSignIn(gsc)
        }

        val ss = SpannableString("Belum punya akun? Daftar")
        val clickableSignup : ClickableSpan = object : ClickableSpan(){
            override fun onClick(p0 : View) {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
        }

        ss.setSpan(clickableSignup, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtSignup.text = ss
        binding.txtSignup.movementMethod = LinkMovementMethod.getInstance()

        binding.txtForgotPass.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        fBaseAuth = Firebase.auth
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fBaseAuth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        fBaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = fBaseAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val landingPageIntent = Intent(applicationContext, LandingPageActivity::class.java)
            landingPageIntent.putExtra(ACCOUNT_NAME, user.displayName)
            landingPageIntent.putExtra(ACCOUNT_EMAIL, user.email)
            landingPageIntent.putExtra(ACCOUNT_PHOTO_URL, user.photoUrl)
            startActivity(landingPageIntent)
        }
    }
    private fun googleSignIn(gsc : GoogleSignInClient) {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
        var ACCOUNT_NAME = "Guest"
        var ACCOUNT_EMAIL = "Email"
        var ACCOUNT_PHOTO_URL = "NULL"
    }

}

