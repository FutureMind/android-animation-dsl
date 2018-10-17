# Android Animators DSL
Small yet very useful set of Kotlin extension functions to build Android animations more easy and straightforward.

## Usage:

```kotlin
playSequentially {
            onAnimationEnd { /* Animation finished */) }

            play { /* Animator object */ }
            play { ObjectAnimator.ofFloat(view1, View.ALPHA, 0f, 1f) }
            play { view1 translateTo container.topLeft() }
            play { view2 translateTo container.bottomLeft() }

            playTogether {
                startDelay = 2000L
                duration = 1000L
                interpolator = FastOutSlowInInterpolator()

                play { view1 translateTo container.topRight() }
                play { view2 translateTo container.bottomRight() }
            }

            playTogether {
                onAnimationStart { /* Animation started */ }
                play { view1 translateTo container.center() withDuration 2000 withInterpolator OvershootInterpolator() }
                play { view2 translateTo container.center() withDuration 2000 withInterpolator AccelerateDecelerateInterpolator() }
            }
        }.start()
```

## Installation:

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```groovy
dependencies {
    implementation 'com.github.FutureMind:android-animation-dsl:1.0'
}
```