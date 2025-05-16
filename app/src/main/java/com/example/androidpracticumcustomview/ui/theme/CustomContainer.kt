package com.example.androidpracticumcustomview.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    private val verticalMargin = context.resources.getDimensionPixelSize(R.dimen.padding_vertical)

    var durationViewsAlpha: Long = 2000
    var durationViewsMoving: Long = 5000

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        children.forEach { child ->
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        /**
         * Для каждого дочернего элемента указывается изначальная позиция в центре экрана.
         * Затем, к ним применяются анимации (в зависимости от их индекса) и индивидуальная стилизация background'a
         */
        children.forEachIndexed { index, child ->
            setInitialLayout(child)
            setAnimationOnView(index, child)
        }
    }

    /**
     * Метод предназначен для размещения дочерних View по центру экрана
     */
    private fun setInitialLayout(child: View) {
        val centerHorizontal = measuredWidth / 2
        val centerVertical = measuredHeight / 2
        val leftSide = centerHorizontal - child.measuredWidth / 2
        val rightSide = centerHorizontal + child.measuredWidth / 2
        val bottomSide = centerVertical + child.measuredHeight / 2
        val topSide = centerVertical - child.measuredHeight / 2
        child.layout(leftSide, topSide, rightSide, bottomSide)
    }

    /**
     * Метод предназначен для наложения анимаций на дочерний View
     * Каждому View присваивается значение прозрачности 0, в соответствии с заданием
     * Затем, в зависимости от индекса дочернего View им присваивается анимация передвижения по оси Y
     * к своим требуемым позициям
     */
    private fun setAnimationOnView(index: Int, child: View) {
        child.alpha = 0f
        val translationValue =
            (measuredHeight - child.measuredHeight) / 2 - verticalMargin.toFloat()
        child.animate().alpha(1f).setDuration(durationViewsAlpha)
        when (index) {
            0 -> {
                child.setBackgroundResource(R.drawable.top_view_background)
                child.animate().translationY(-translationValue).setDuration(durationViewsMoving)
            }

            1 -> {
                child.setBackgroundResource(R.drawable.bottom_view_background)
                child.animate().translationY(translationValue).setDuration(durationViewsMoving)
            }
        }
    }

    override fun addView(child: View) {
        if (children.count() < 2) {
            super.addView(child)
        } else {
            throw IllegalStateException()
        }
    }
}