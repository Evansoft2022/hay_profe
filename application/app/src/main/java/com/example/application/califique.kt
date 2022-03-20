package com.example.application

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

class califique : AppCompatActivity() {

    private lateinit var star : ImageView
    private lateinit var test : ImageView
    private val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
    private val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_califique)
        star = findViewById(R.id.star)
        test = findViewById(R.id.imgtest)


        val path = "https://ak.picdn.net/shutterstock/videos/4924187/thumb/1.jpg?ip=x480"
        Picasso.get().load(path).into(test)

        star.setOnClickListener {
            scaler()
            rotater()
        }
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION,
                                              -360f, 0f)
        animator.duration = 1000
        animator.start()
    }

    private fun scaler() {
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            star, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        //disableViewDuringAnimation(star,animator)
        animator.start()

    }
    private fun disableViewDuringAnimation(view: View,
                                           animator: ObjectAnimator) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }
}