package com.yudhis.studyhive

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding
    private lateinit var gsc : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize firebase authorization
        auth = Firebase.auth

        // make the "Daftar" part of "Belum punya akun? Daftar" clickable
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

        // forgot password text on click listener
        binding.txtForgotPass.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // regular login handling
        binding.btnLogin.setOnClickListener{
            val email = binding.fieldEmail.text
            val password = binding.fieldPass.text
            if (TextUtils.isEmpty(email)) {
                binding.fieldEmail.error = "Email harus diisi"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.fieldEmail.error = "Email invalid"
            }

            if (TextUtils.isEmpty(password)) {
                binding.fieldPass.error = "Password harus diisi"
            }
            else {
                signIn(email.toString(), password.toString())
            }
        }

        // Google sign in
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("421961381611-bb2tb0klvq2mc13n3lp920sb39krsa17.apps.googleusercontent.com")
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        binding.btnGoogleLogin.setOnClickListener{
            signIn()
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
                Log.w(TAG, "Google sign in failed ${e.message}")
                Log.w(TAG, "Google sign in failed", e.cause)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    // google sign in
    private fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // password based sign in
    private fun signIn(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    Log.w(TAG, "signInWithEmail:failure", exception)
                    Toast.makeText(baseContext, "Autentikasi Gagal",
                        Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser : FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user : FirebaseUser?) {
        if (user != null) {
            //go to landing page
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
//        var ACCOUNT_INFO : HashMap<String, String> = hashMapOf()
    }

}

