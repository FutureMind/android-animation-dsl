package com.fm.android.animatorsdsl

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

fun animateTogether(block: AnimateTogetherBuilder.() -> Unit): AnimatorSet {
    val set = AnimateTogetherBuilder()
            .apply(block)
    return set.build()
}

fun animateSequence(block: AnimateSequenceBuilder.() -> Unit): AnimatorSet {
    val set = AnimateSequenceBuilder()
            .apply(block)
    return set.build()
}


class AnimateSequenceBuilder : AnimatorBuilder() {
    override fun build(): AnimatorSet {
        return AnimatorSet().apply {
            initAnimationProperties()
            playSequentially(animators)
        }
    }
}

class AnimateTogetherBuilder : AnimatorBuilder() {
    override fun build(): AnimatorSet {
        return AnimatorSet().apply {
            initAnimationProperties()
            playTogether(animators)
        }
    }
}

open class AnimatorBuilder {
    var duration: Long = -1L
    val animators: MutableList<Animator> = mutableListOf()
    var interpolator: Interpolator? = null

    open fun build(): AnimatorSet = AnimatorSet()
            .apply {
                initAnimationProperties()
                playTogether(animators)
            }

    protected fun AnimatorSet.initAnimationProperties() {
        if (this@AnimatorBuilder.duration != -1L) {
            duration = this@AnimatorBuilder.duration
        }
        if (this@AnimatorBuilder.interpolator != null) {
            interpolator = this@AnimatorBuilder.interpolator
        }
    }

    fun withInterpolator(block: () -> Interpolator) {
        interpolator = block()
    }

    fun animate(block: () -> Animator) {
        this.animators.add(block())
    }

    fun animateTogether(block: AnimateTogetherBuilder.() -> Unit) {
        this.animators.add(AnimateTogetherBuilder().apply(block).build())
    }

    fun animateSequence(block: AnimateSequenceBuilder.() -> Unit) {
        this.animators.add(AnimateSequenceBuilder().apply(block).build())
    }
}