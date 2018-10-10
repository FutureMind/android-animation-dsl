package com.fm.android.animatordsl.lib

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator

fun playTogether(block: PlayTogetherBuilder.() -> Unit): AnimatorSet {
    val set = PlayTogetherBuilder()
            .apply(block)
    return set.build()
}

fun playSequentially(block: PlaySequentiallyBuilder.() -> Unit): AnimatorSet {
    val set = PlaySequentiallyBuilder()
            .apply(block)
    return set.build()
}


class PlaySequentiallyBuilder : PlayTogetherBuilder() {
    override fun build(): AnimatorSet {
        return AnimatorSet().apply {
            injectAnimatorProperties()
            playSequentially(animators)
        }
    }
}


open class PlayTogetherBuilder {
    var duration: Long? = null
    val animators: MutableList<Animator> = mutableListOf()
    var interpolator: Interpolator? = null

    open fun build(): AnimatorSet {
        return AnimatorSet().apply {
            injectAnimatorProperties()
            playTogether(animators)
        }
    }

    protected fun AnimatorSet.injectAnimatorProperties() {
        this@PlayTogetherBuilder.duration?.let { duration = it }
        this@PlayTogetherBuilder.interpolator?.let { interpolator = it }
    }

    fun play(block: () -> Animator) {
        this.animators.add(block())
    }

    fun playTogether(block: PlayTogetherBuilder.() -> Unit) {
        this.animators.add(PlayTogetherBuilder().apply(block).build())
    }

    fun playSequentially(block: PlaySequentiallyBuilder.() -> Unit) {
        this.animators.add(PlaySequentiallyBuilder().apply(block).build())
    }
}