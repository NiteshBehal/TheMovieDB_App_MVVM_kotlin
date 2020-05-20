package com.learning.mvvmmovieapp.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.learning.mvvmmovieapp.data.api.POSTER_BASE_URL
import java.text.NumberFormat
import java.util.*

@BindingAdapter("convertCurrency")
fun convertCurrency1(view: TextView, currency: Double) {
    val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
    view.text = formatCurrency.format(currency)
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(POSTER_BASE_URL + imageUrl)
        .into(view)
}