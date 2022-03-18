package com.aifubook.book.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CompoundDrawableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun setCompoundDrawables(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        top?.setBounds(
            0,
            0,
            dp2px(this.context, 24f),
            dp2px(this.context, 24f)
        )
        super.setCompoundDrawables(left, top, right, bottom)
    }


    private fun dp2px(context: Context, dp: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dp * scale + 0.5f).toInt()
    }
}