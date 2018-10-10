package com.fm.android.animatorsdsl

import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
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

        animateSequence {

            animate { view1 translateTo container.topLeft() }

            animate { view2 translateTo container.bottomLeft() }

            animateTogether {

                animate { view1 translateTo container.topRight() }

                animate { view2 translateTo container.bottomRight() }
            }

            animateTogether{

                animate { view1 translateTo container.center() withDuration 2000 withInterpolator OvershootInterpolator() }

                animate { view2 translateTo container.center() withDuration 2000 withInterpolator AccelerateDecelerateInterpolator() }
            }

        }.start()
    }
}
