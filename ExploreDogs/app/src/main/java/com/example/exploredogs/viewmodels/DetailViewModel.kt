package com.example.exploredogs.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exploredogs.models.Dogbreed
import com.example.exploredogs.models.dogDatabse
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val livedatadog = MutableLiveData<Dogbreed>()

    fun fetch(uuid:Int) {
       launch {
           val dog=dogDatabse(getApplication()).dogdao().getDog(uuid)
           livedatadog.value = dog
       }

    }
}