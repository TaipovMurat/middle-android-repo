package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты.
*/

@Composable
fun MainScreen(closeActivity: () -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clickable { closeActivity.invoke() },
            contentAlignment = Alignment.Center
        ) {

            CustomContainerCompose(
                firstChild = {
                    Text(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_vertical)),
                        text = "Вверх!",
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                },
                secondChild = {
                    Text(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_vertical)),
                        text = "Высота view\nне\nважна",
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                }
            )
        }
    }
}