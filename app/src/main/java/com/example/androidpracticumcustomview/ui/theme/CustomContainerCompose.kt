package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import com.example.androidpracticumcustomview.R
import com.example.androidpracticumcustomview.utils.DEFAULT_DENSITY
import com.example.androidpracticumcustomview.utils.DURATION_VIEWS_ALPHA
import com.example.androidpracticumcustomview.utils.DURATION_VIEWS_TRANSLATION
import kotlinx.coroutines.launch


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?,
    durationViewsAlpha: Int = 2000,
    durationViewsMoving: Int = 5000
) {

    /**
     * Вычисление размеров экрана в зависимости от плотности экрана
     */
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val density = LocalConfiguration.current.densityDpi / DEFAULT_DENSITY
    val screenHeightPx = screenHeightDp * density


    /**
     * Инициализация переменных, участвующих в анимации
     */
    val alpha = remember { Animatable(0f) }
    val offsetFirstChild = remember { Animatable(0f) }
    val offsetSecondChild = remember { Animatable(0f) }

    /**
     * Переменные необходимые для получения высоты дочерних View
     */
    var firstChildHeight = 0f
    var secondChildHeight = 0f


    /**
     * Переменные для определения значений скругления углов контейнеров
     */
    val cornerRadius = dimensionResource(R.dimen.corners_round)
    val cornerShape = remember(cornerRadius) {
        RoundedCornerShape(cornerRadius)
    }

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        /**
         * Для одновременного отображения всех анимаций, для каждого анимированого параметра
         * используется отдельная корутина
         */
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(DURATION_VIEWS_ALPHA.toInt())
            )
        }

        launch {
            offsetFirstChild.animateTo(
                targetValue = -(screenHeightPx / 2 - firstChildHeight / 2),
                animationSpec = tween(DURATION_VIEWS_TRANSLATION.toInt())
            )
        }

        launch {
            offsetSecondChild.animateTo(
                screenHeightPx / 2 - secondChildHeight / 2,
                animationSpec = tween(DURATION_VIEWS_TRANSLATION.toInt())
            )
        }
    }

    // Основной контейнер
    /**
     * Контейнер занимает всю ширину и высоту экрана
     */
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (firstChild != null) {
            Box(modifier = Modifier
                .onGloballyPositioned {
                    firstChildHeight = it.size.height.toFloat()
                }
                .graphicsLayer {
                    this.alpha = alpha.value
                    this.translationY = offsetFirstChild.value
                }
                .border(
                    width = dimensionResource(R.dimen.stroke_width),
                    shape = cornerShape,
                    color = Color.Cyan
                )
            ) {
                firstChild()
            }
        }

        if (secondChild != null) {
            Box(modifier = Modifier
                .onGloballyPositioned {
                    secondChildHeight = it.size.height.toFloat()
                }
                .graphicsLayer {
                    this.alpha = alpha.value
                    this.translationY = offsetSecondChild.value
                }
                .border(
                    width = dimensionResource(R.dimen.stroke_width),
                    shape = cornerShape,
                    color = Color.Magenta
                )) {
                secondChild()
            }
        }

    }
}