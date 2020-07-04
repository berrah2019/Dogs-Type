package com.example.exploredogs.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.exploredogs.R
import com.example.exploredogs.databinding.FragmentDetailBinding
import com.example.exploredogs.models.DogPalette
import com.example.exploredogs.viewmodels.DetailViewModel


class DetailFragment : Fragment() {

    lateinit var viewmodel: DetailViewModel
    private var doguiddd = 0
    private lateinit var databinding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_detail, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        arguments.let {
            doguiddd = DetailFragmentArgs.fromBundle(it!!).doguid
        }
        viewmodel.fetch(doguiddd)
        observeviewmodel()
    }

    private fun observeviewmodel() {
        viewmodel.livedatadog.observe(this, Observer { dog ->
            dog?.let {
                /* doggname.text = it.dogBreed
                 dogpurpose.text = it.bredFor
                 dogtemperament.text = it.temperament
                 doglifespan.text = it.lifeSpan
                 context?.let {
                     doggImage.loadimage(dog.imageUrl, getprogressdrawable(it))

                 }*/
                databinding.dog = dog
                it.imageUrl?.let {
                    setUpBackgroundImage(it)
                }
            }
        })
    }

    fun setUpBackgroundImage(url: String) {
     Glide.with(this)
         .asBitmap()
         .load(url)
         .into(object:CustomTarget<Bitmap>(){
             override fun onLoadCleared(placeholder: Drawable?) {}

             override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource)
                    .generate{
                        val intcolor=it?.vibrantSwatch?.rgb?:0
                        val myPalette=DogPalette(intcolor)
                        databinding.palette=myPalette
                    }
             }

         })
    }

}