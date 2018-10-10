package com.fm.android.animatorsdsl.app

import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.fm.android.animatorsdsl.R
import com.fm.android.animatorsdsl.lib.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener { animate() }
    }

    private fun animate() {

        view1.alpha = .5f
        view2.alpha = .5f

        playSequentially {

            onAnimationStart { log("Start sequence") }

            onAnimationEnd { log("End sequence") }

            play { view1 translateTo container.topLeft() }

            play { view2 translateTo container.bottomLeft() }

            playTogether {

                duration = 5000L

                interpolator = FastOutSlowInInterpolator()

                onAnimationStart { log("Start together") }

                onAnimationEnd { log("End together") }

                play { view1 translateTo container.topRight() }

                play { view2 translateTo container.bottomRight() }
            }

            playTogether {

                play { view1 translateTo container.center() withDuration 2000 withInterpolator OvershootInterpolator() }

                play { view2 translateTo container.center() withDuration 2000 withInterpolator AccelerateDecelerateInterpolator() }
            }

        }.start()

        playTogether {
            duration = 1000L
            play { view1 alphaTo 1f }
            play { view2 alphaTo 1f }
        }.start()
    }

    private fun log(message: String) {
        Log.d("AnimatorsDSLLog", message)
    }
}
