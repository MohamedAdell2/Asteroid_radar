package com.udacity.asteroidradar.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay

@BindingAdapter("list")
fun submitList (recyclerView: RecyclerView , asteroids :List<Asteroid>?){
    val adapter =recyclerView.adapter as AsteroidAdapter
    adapter.submitList(asteroids)
}

@BindingAdapter("PicBind")
fun bindPicOfTheDay (imageView: ImageView , pictureOfDay: PictureOfDay?){
    if ((pictureOfDay != null) && (pictureOfDay.mediaType == "image")){
        val url = pictureOfDay.url
        Picasso.get().load(url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .into(imageView)
        imageView.contentDescription=pictureOfDay.title
    }else{
        imageView.setImageResource(R.drawable.logo)
        imageView.contentDescription="No image today"
    }
}

@BindingAdapter("load")
fun loadList(view: View , list :Any?){
    view.visibility = if (list!= null) View.GONE else View.VISIBLE
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription="Asteroid is hazardous"
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription="Asteroid is safe"
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription="Asteroid is hazardous"
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription="Asteroid is safe"
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
