package com.lupesoft.mvppattern.presenter.extensions

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.lupesoft.mvppattern.BuildConfig.BaseUrlImage
import com.lupesoft.mvppattern.R


fun View.isHide(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun AppCompatImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(BaseUrlImage + url)
            .override(
                context.resources.getDimension(R.dimen.imageWidth).toInt(),
                context.resources.getDimension(R.dimen.imageHeight).toInt()
            )
            .into(this)
    }
}

fun AppCompatImageView.loadImageDetail(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(BaseUrlImage + url)
            .override(
                context.resources.getDimension(R.dimen.imageDetailWidth).toInt(),
                context.resources.getDimension(R.dimen.imageDetailHeight).toInt()
            )
            .into(this)
    }
}
