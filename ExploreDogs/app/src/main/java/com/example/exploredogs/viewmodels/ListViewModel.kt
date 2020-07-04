package com.example.exploredogs.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.exploredogs.Util.NotificationsHelper
import com.example.exploredogs.Util.sharedPreferencesHelper
import com.example.exploredogs.models.DogApiService
import com.example.exploredogs.models.Dogbreed
import com.example.exploredogs.models.dogDatabse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ListViewModel(application: Application) : BaseViewModel(application) {
    private var prefHelper = sharedPreferencesHelper(getApplication())
    private var timeRefresh = 5 * 1000 * 1000 * 1000L
    var dogs = MutableLiveData<List<Dogbreed>>()
    var dogsloadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()

    private val dogService = DogApiService()
    private val disposable = CompositeDisposable()
    fun refresh() {
        CheckCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < timeRefresh) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun CheckCacheDuration() {
      val cashePreference=prefHelper.getCacheDuration()
        try{
            val cashePreferenceint=cashePreference?.toInt()?:5*60
            timeRefresh=cashePreferenceint.times( 1000 * 1000 * 1000L)
        }catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }

    fun refreshByPass(){
        fetchFromRemote()
    }
    private fun fetchFromDatabase(){
        loading.value=true
        launch {
            val dogs=dogDatabse(getApplication()).dogdao().getAllDogs()
            dogReteive(dogs)
            Toast.makeText(getApplication(),"Dog retreived from database",Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogService.getDogs().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Dogbreed>>() {
                    override fun onSuccess(listDog: List<Dogbreed>) {
                        storeDogLocally(listDog)
                        Toast.makeText(getApplication(),"Dog retreived from API",Toast.LENGTH_SHORT).show()
                        NotificationsHelper(getApplication()).create_notification()
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        dogsloadError.value = true
                        e.printStackTrace()
                    }

                })


        )
    }

    private fun dogReteive(t: List<Dogbreed>) {
        dogs.value = t

        loading.value = false
        dogsloadError.value = false
    }

    private fun storeDogLocally(list: List<Dogbreed>) {
        launch {
            val doa = dogDatabse(getApplication()).dogdao()
            doa.deleteAllDogs()
            val result = doa.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogReteive(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}