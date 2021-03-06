package com.mentalmachines.droidcon_boston.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mentalmachines.droidcon_boston.R
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.splash_activity.logo_text


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        Handler().postDelayed(this::fadeImage, SPLASH_DURATION - FADE_DURATION)
        Handler().postDelayed(this::startMainActivity, SPLASH_DURATION)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
        finish()
    }

    private fun fadeImage() {
        val a = AlphaAnimation(1.00f, 0.00f)

        a.duration = FADE_DURATION
        a.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) = logo_text.setVisibility(View.GONE)
        })

        logo_text.startAnimation(a)
    }

    companion object {
        val FADE_DURATION: Long = 400
        val SPLASH_DURATION: Long = 1100
    }
}
