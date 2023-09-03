package com.roman.jpmnycsch.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.jpmnycsch.model.AppDatabase
import com.roman.jpmnycsch.model.SATScore
import com.roman.jpmnycsch.model.School
import com.roman.jpmnycsch.model.SchoolRetrofitClient
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/*
* Class: SchoolViewModel
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27
*/

class SchoolViewModel(application: Application): BaseViewModel(application)  {
    private val tag = javaClass.simpleName

    private val schoolApiService = SchoolRetrofitClient.getInstance()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _:CoroutineContext, _:Throwable ->
    onError("Exception: $(throwable.localizedMessage)")
}
    private val disposable = CompositeDisposable()
    val schools = MutableLiveData<List<School>>()
    private val satScores = MutableLiveData<List<SATScore>>()
    val dbn =  MutableLiveData<String?>() //District Borough Number (New York City Department of Education school identifier)
    val schoolLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    val schoolRetClient = SchoolRetrofitClient.getInstance()

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun refresh(){
        fetchSchools()
    }

    private fun fetchSchools() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response: Response<List<School>> = schoolApiService.getAllSchools()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    schools.value = response.body()
                    schoolLoadError.value = false
                    loading.value = false
                } else {
                    onError(" --> Error: ${response.message()}")
                }
            }

        }

    }

    private fun onError(message: String) {
        schoolLoadError.value = true
        loading.value = false
        Log.e(tag, message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val scores = AppDatabase(getApplication()).satScoreDao().getAllSATScores()
            satScoreRetrieved(scores)
            Toast.makeText(getApplication(), "SATScore retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun satScoreRetrieved(satScoreList: List<SATScore>) {
        satScores.value = satScoreList
        schoolLoadError.value = false
        loading.value = false
    }

/*    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            schoolRetClient.getSatScores()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<SATScore>>() {

                    override fun onSuccess(scoreList: List<SATScore>) {
                        storeSATScoreLocally(scoreList)
                        Toast.makeText(getApplication(), "SATScoreList retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        NotificationsHelper(getApplication()).createNofitication()
                    }

                    override fun onError(e: Throwable) {
                        schoolLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }*/

   /* private fun storeSATScoreLocally(list: List<SATScore>) {
        launch {
            val dao = AppDatabase(getApplication()).satScoreDao()
            dao.deleteAllSAtScore()
            val result = dao.insertAllSATScores(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toString().toInt()
                ++i
            }
            satScoreRetrieved(list)
        }
    }*/

}