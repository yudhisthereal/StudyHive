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
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.databinding.ActivityLoginBinding
import java.io.InputStream

class LoginActivity : AppCompatActivity() {

    private lateinit var fBaseAuth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Google sign in
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("421961381611-c33pgbcqcjuvi3r01aqjors21ogsd8rh.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val gsc : GoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnGoogleLogin.setOnClickListener{
            googleSignIn(gsc)
        }

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
            if (TextUtils.isEmpty(binding.fieldEmail.text)) {
                binding.fieldEmail.error = "Email harus diisi"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.fieldEmail.text).matches()) {
                binding.fieldEmail.error = "Email invalid"
            }
            else {
                //check account existence
                if (!accountExists(binding.fieldEmail.text.toString()))
                {
                    binding.fieldEmail.error = "Email ini belum terdaftar"
                    return@setOnClickListener
                }
            }

            if (TextUtils.isEmpty(binding.fieldPass.text)) {
                binding.fieldPass.error = "Password harus diisi"
            }
            else {
                //get account info
                val accountInfo : HashMap<String, String> = getUserRecord(assets.open("users.csv"), binding.fieldEmail.text.toString())

                //match password
                if (accountInfo["UserPassword"] != binding.fieldPass.text.toString()) {
                    binding.fieldPass.error = "Password salah"
                }
                else {
                    signIn(accountInfo)
                }
            }

        }

        // initialize firebase authorization
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
            startActivity(landingPageIntent)
            ACCOUNT_INFO["UserName"] = user.displayName.toString()
            ACCOUNT_INFO["AccountEmail"] = user.email.toString()
        }
    }
    private fun googleSignIn(gsc : GoogleSignInClient) {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signIn(accountInfo : HashMap<String, String>) {
        val landingPageIntent = Intent(this, LandingPageActivity::class.java)
        ACCOUNT_INFO = accountInfo
        startActivity(landingPageIntent)
        finish()
        //SIGN IN
    }

    private fun accountExists(email : String) : Boolean {
        val istream = assets.open("users.csv")
        val reader = istream.bufferedReader()
        var rowStr : String = reader.readLine()
        var found = false
        while (rowStr.isNotBlank()) {
            var record = rowStr.split(",").toMutableList()
            for (i in record.indices) {
                record[i] = record[i].trim()
            }
            if (record[1] == email) {
                found = true
                break
            }
            try {
                rowStr = reader.readLine()
            } catch (e : NullPointerException) {
                break
            }
        }
        return found
    }
    private fun getUserRecord(istream : InputStream, email : String) : HashMap<String, String> {
        var record : HashMap<String, String> = hashMapOf()
        val reader = istream.bufferedReader()
        var header = reader.readLine().split(",").toMutableList()
        for (i in header.indices) {
            header[i] = header[i].trim()
        }
        var rowStr : String = reader.readLine()
        while(rowStr.isNotBlank() && rowStr != null) {
            val currentRecord = rowStr.split(",").toMutableList()
            currentRecord[1] = currentRecord[1].trim()
            if (currentRecord[1] == email) {
                for (i in currentRecord.indices) {
                    currentRecord[i] = currentRecord[i].trim()
                }
                record = header.zip(currentRecord).toMap()  as HashMap<String, String>
                break
            }
            try {
                rowStr = reader.readLine()
            } catch (e : NullPointerException) {
                break
            }
        }
        return record
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
        var ACCOUNT_INFO : HashMap<String, String> = hashMapOf()
    }

}

