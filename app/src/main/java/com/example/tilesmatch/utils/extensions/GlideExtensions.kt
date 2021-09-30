package com.example.tilesmatch.utils.extensions

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/**
 * @return glide instance for fragment
 */
fun Fragment.glide() = Glide.with(this)