package com.fm.android.animatordsl.lib

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator

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


class AnimateSequenceBuilder : AnimateTogetherBuilder() {
    override fun build(): AnimatorSet {
        return AnimatorSet().apply {
            initAnimationProperties()
            playSequentially(animators)
        }
    }
}


open class AnimateTogetherBuilder {
    var duration: Long? = null
    val animators: MutableList<Animator> = mutableListOf()
    var interpolator: Interpolator? = null

    open fun build(): AnimatorSet {
        return AnimatorSet().apply {
            initAnimationProperties()
            playTogether(animators)
        }
    }

    protected fun AnimatorSet.initAnimationProperties() {
        this@AnimateTogetherBuilder.duration?.let { duration = it }
        this@AnimateTogetherBuilder.interpolator?.let { interpolator = it }
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