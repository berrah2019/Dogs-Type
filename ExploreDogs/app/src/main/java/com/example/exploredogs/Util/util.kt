package com.example.exploredogs.Util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exploredogs.R
import java.security.AccessControlContext

fun getprogressdrawable(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=10f
        centerRadius=50f
        start()
    }
}
fun ImageView.loadimage(uri:String?,progressdrawable:CircularProgressDrawable){
    val option=RequestOptions.placeholderOf(progressdrawable)
        .error(R.mipmap.ic_dog)
    Glide.with(context).setDefaultRequestOptions(option)
        .load(uri).into(this)
}
@BindingAdapter("android:image_Url")
fun loadImage(view: ImageView,uri: String?){
    view.loadimage(uri, getprogressdrawable(view.context))
}