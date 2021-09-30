package com.example.tilesmatch.utils.extensions

import android.view.View

/**
 * makes view visibility VISIBLE
 */
fun View.visible() {
    if (!isVisible()) visibility = View.VISIBLE
}

/**
 * makes view visibility GONE
 */
fun View.gone() {
    if (!isGone()) visibility = View.GONE
}

/**
 * @return flag denoting if the view is visible or not
 */
fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

/**
 * @return flag denoting if the view is gone or not
 */
fun View.isGone(): Boolean {
    return visibility == View.GONE
}