package com.fm.android.animatorsdsl

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.PointF
import android.view.View
import android.view.animation.Interpolator


infix fun View.translateTo(position: PointF): AnimatorSet {
    val translateX = position.x - x - measuredWidth / 2
    val translateY = position.y - y - measuredHeight / 2

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(
            ObjectAnimator.ofFloat(this, View.TRANSLATION_X.name, translateX),
            ObjectAnimator.ofFloat(this, View.TRANSLATION_Y.name, translateY))
    return animatorSet
}

infix fun View.alphaTo(alphaValue: Float) = ObjectAnimator.ofFloat(this, View.ALPHA.name, alphaValue)

infix fun Animator.withInterpolator(newInterpolator: Interpolator) = this.apply { interpolator = newInterpolator }

infix fun Animator.withDuration(newDuration: Long) = this.apply { duration = newDuration }
