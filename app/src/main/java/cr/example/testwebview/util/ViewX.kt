package cr.example.testwebview.util

import android.content.res.ColorStateList
import android.graphics.drawable.*
import android.view.View
import android.R.attr


fun View.getBackgroundColor() : Int {
    when (val background: Drawable = this.background) {
        is ShapeDrawable -> {
            return background.paint.color
        }
        is ColorDrawable -> {
            return background.color
        }
    }
    return 0
}

fun View.changeBackgroundColor(color: Int) {
    when (val background: Drawable = this.background) {
        is ShapeDrawable -> {
            background.paint.color = color
        }
        is GradientDrawable -> {
            background.setColor(color)
        }
        is ColorDrawable -> {
            background.color = color
        }
        is RippleDrawable -> {
            val colors = intArrayOf(color, color, color, color, color)
            val states = arrayOf(intArrayOf(attr.state_enabled),
                intArrayOf(attr.state_active),
                intArrayOf(attr.state_activated),
                intArrayOf(attr.state_empty),
                intArrayOf()


                )
            val colorStateList = ColorStateList(states, colors)
            background.setColor(colorStateList)
        }
    }
}

