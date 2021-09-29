package com.example.tilesmatch.utils.extensions

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun Fragment.glide() = Glide.with(this)