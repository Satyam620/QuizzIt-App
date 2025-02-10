package com.venom.quizzapp.screens

import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.venom.quizzapp.R

@Composable
fun AnimatedLogo() {
    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                setImageResource(R.drawable.animated_logo) // Load AVD

                fun startAnimationLoop() {
                    post {
                        val drawable = drawable
                        if (drawable is AnimatedVectorDrawable) {
                            drawable.start()
                            postDelayed(
                                { startAnimationLoop() },
                                500
                            ) // Adjust delay to match animation duration
                        } else if (drawable is AnimatedVectorDrawableCompat) {
                            drawable.start()
                            postDelayed({ startAnimationLoop() }, 500)
                        }
                    }
                }

                startAnimationLoop() // Start the looping animation
            }
        },
        modifier = Modifier
    )
}