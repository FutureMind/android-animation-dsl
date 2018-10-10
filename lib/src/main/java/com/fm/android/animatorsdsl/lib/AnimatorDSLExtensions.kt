package com.fm.android.animatorsdsl.lib

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
    var startDelay: Long? = null
    var duration: Long? = null
    val animators: MutableList<Animator> = mutableListOf()
    var interpolator: Interpolator? = null

    private var onAnimationStartAction: (() -> Unit)? = null
    private var onAnimationEndAction: (() -> Unit)? = null

    open fun build(): AnimatorSet {
        return AnimatorSet().apply {
            injectAnimatorProperties()
            playTogether(animators)
        }
    }

    protected fun AnimatorSet.injectAnimatorProperties() {
        this@PlayTogetherBuilder.duration?.let { duration = it }
        this@PlayTogetherBuilder.startDelay?.let { startDelay = it }
        this@PlayTogetherBuilder.interpolator?.let { interpolator = it }

        if (onAnimationStartAction != null || onAnimationEndAction != null) {
            addListener(ActionAnimatorListener(onAnimationStartAction, onAnimationEndAction))
        }
    }

    fun onAnimationStart(action: (() -> Unit)?) {
        onAnimationStartAction = action
    }

    fun onAnimationEnd(action: (() -> Unit)?) {
        onAnimationEndAction = action
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

class ActionAnimatorListener(private val startAction: (() -> Unit)?,
                             private val endAction: (() -> Unit)?) : AnimatorListenerAdapter() {

    override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)
        endAction?.invoke()
    }

    override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        startAction?.invoke()
    }

}