# Android Animators DSL
Small yet very useful set of Kotlin extension functions to build Android animations more easy and straightforward.

## Usuage:

```kotlin
playSequentially {
            onAnimationEnd { /* Animation finished */) }

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

## Instalation:

```gradle
TODO()
```