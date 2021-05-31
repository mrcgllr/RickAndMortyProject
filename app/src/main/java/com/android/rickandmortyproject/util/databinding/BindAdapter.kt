package com.android.rickandmortyproject.util.databinding

import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.android.rickandmortyproject.R
import com.bumptech.glide.Glide

object BindAdapter {

    @JvmStatic
    @BindingAdapter("bindImageUrl")
    fun bindImage(imageView: ImageView, imageUrl: String?) {
               if (!imageUrl.isNullOrEmpty())
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bindStatusColor")
    fun bindStatusColor(view: ImageView, status: String?) {
        status?.let {
            when (it) {
                "Alive" -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        view.resources,
                        R.color.green,
                        null
                    )
                )
                "Dead" -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        view.resources,
                        R.color.red,
                        null
                    )
                )
                else -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        view.resources,
                        R.color.gray,
                        null
                    )
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindGenderIcon")
    fun bindGenderIcon(imageView: ImageView, gender: String?) {
        if (!gender.isNullOrEmpty()) {
            when (gender) {
                "Male" -> imageView.background =
                    ResourcesCompat.getDrawable(imageView.resources, R.drawable.ic_male, null)
                else -> imageView.background =
                    ResourcesCompat.getDrawable(imageView.resources, R.drawable.ic_female, null)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindFavIcon")
    fun bindFavIcon(imageView: ImageView, isFav: Boolean) {
        when (isFav) {
            true -> imageView.background =
                ResourcesCompat.getDrawable(imageView.resources, R.drawable.ic_fav, null)
            else -> imageView.background =
                ResourcesCompat.getDrawable(imageView.resources, R.drawable.ic_no_fav, null)
        }

    }
}