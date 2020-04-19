package com.example.genealogy_app.Classes

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent


class NoSwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    //to be honest, I thought this would disable all page changes by swiping,
    //  but it only affects the tree fragment, and you can still change tabs by swiping on the profile list
    //  and profile page. Honestly, that might be better than what I thought it would do, so I'm keeping it.
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

}