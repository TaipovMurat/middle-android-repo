package com.example.androidpracticumcustomview

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.ui.theme.CustomContainer


class XmlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startXmlPracticum()
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)
        customContainer.setOnClickListener {
            finish()
        }

        val firstView = TextView(this).apply {
            text = "Простите"
            textAlignment = ViewGroup.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setPadding(
                context.resources.getDimensionPixelSize(R.dimen.padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.padding_vertical),
                context.resources.getDimensionPixelSize(R.dimen.padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.padding_vertical),
            )
        }

        val secondView = TextView(this).apply {
            text = "За просроченный дэдлайн"
            textAlignment = ViewGroup.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setPadding(
                context.resources.getDimensionPixelSize(R.dimen.padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.padding_vertical),
                context.resources.getDimensionPixelSize(R.dimen.padding_horizontal),
                context.resources.getDimensionPixelSize(R.dimen.padding_vertical),
                )
        }

        customContainer.addView(firstView)
        customContainer.addView(secondView)

    }
}