package com.noxpa.exchangeratestask.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.noxpa.exchangeratestask.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolbarView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_toolbar, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView)

        try {
            setImageLeft(attributes.getDrawable(R.styleable.ToolbarView_toolbar_view_image_left))
            setImageRight(attributes.getDrawable(R.styleable.ToolbarView_toolbar_view_image_right))
            setTitle(attributes.getString(R.styleable.ToolbarView_toolbar_view_title))
        } finally {
            attributes.recycle()
        }
    }

    private fun setImageLeft(imageLeft: Drawable?) {
        if (imageLeft == null) {
            image_left_image_view.visibility = View.GONE
        } else {
            image_left_image_view.setImageDrawable(imageLeft)
        }
    }

    private fun setImageRight(imageRight: Drawable?) {
        if (imageRight == null) {
            image_right_image_view.visibility = View.GONE
        } else {
            image_right_image_view.setImageDrawable(imageRight)
        }
    }

    private fun setTitle(title: String?) {
        title_text_view.text = title
    }
}