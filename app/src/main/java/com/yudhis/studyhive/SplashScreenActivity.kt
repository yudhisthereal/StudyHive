package com.yudhis.studyhive

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.Interpolator
import com.yudhis.studyhive.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
        supportActionBar?.hide()

        binding.icSplash.scaleX = 0f
        binding.icSplash.scaleY = 0f



        val xAnimator : ObjectAnimator = ObjectAnimator.ofFloat(binding.icSplash, "scaleX", 1f)
        val yAnimator : ObjectAnimator = ObjectAnimator.ofFloat(binding.icSplash, "scaleY", 1f)
        val animator = AnimatorSet()
        animator.playTogether(xAnimator, yAnimator)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
                finish()
            },
            2000
        )
        animator.start()

    }
}