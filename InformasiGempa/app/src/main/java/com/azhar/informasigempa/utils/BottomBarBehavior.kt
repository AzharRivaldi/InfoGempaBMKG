package com.azhar.informasigempa.utils

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import nl.joery.animatedbottombar.AnimatedBottomBar

/**
 * Created by Azhar Rivaldi on 12-01-2021
 */

class BottomBarBehavior : CoordinatorLayout.Behavior<AnimatedBottomBar>() {

    private var height = 0

    override fun onLayoutChild(parent: CoordinatorLayout, child: AnimatedBottomBar, layoutDirection: Int): Boolean {
        height = child.height
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: AnimatedBottomBar, directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: AnimatedBottomBar, target: View,
                                dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    private fun slideUp(child: AnimatedBottomBar) {
        child.clearAnimation()
        child.animate().translationY(0f).duration = 200
    }

    private fun slideDown(child: AnimatedBottomBar) {
        child.clearAnimation()
        child.animate().translationY(height.toFloat()).duration = 200
    }

}