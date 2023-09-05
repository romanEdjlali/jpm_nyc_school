package com.roman.jpmnycsch.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.roman.jpmnycsch.model.AppDatabase
import com.roman.jpmnycsch.model.SATScore
import com.roman.jpmnycsch.model.School
import com.roman.jpmnycsch.model.NYCSchoolsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//import io.reactivex.rxjava3.disposables.Disposable
//import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/*
* Class: ListViewModel
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27
*/

class ListViewModel(application: Application): BaseViewModel(application)  {
    private val tag = javaClass.simpleName

    private val schoolsService = NYCSchoolsApiService.getInstance()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _:CoroutineContext, _:Throwable ->
    onError("Exception: $(throwable.localizedMessage)")
}
    private val disposable = CompositeDisposable()
    val schools = MutableLiveData<List<School>>()
    private val satScores = MutableLiveData<List<SATScore>>()

    private val singleSatScore = MutableLiveData<SATScore>()

    val dbn =  MutableLiveData<String?>() //District Borough Number (New York City Department of Education school identifier)
    val schoolLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun refresh(){
        fetchSchools()
        deleteDb()
        fetchFromRemote()
        //fetchFromDatabase()
        println("--->>> ${satScores.value?.size}")
    }

    fun deleteDb() {
        launch {
            AppDatabase(getApplication()).satScoreDao().deleteAllSAtScore()
        }
    }

    private fun fetchSchools() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response: Response<List<School>> = schoolsService.getAllSchools()
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
        disposable.clear()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val scores = AppDatabase(getApplication()).satScoreDao().getAllSATScores()
            satScoreRetrieved(scores)
            Toast.makeText(getApplication(), "SATScore retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    //Only retrieved the related info about particular dbn
    private fun fetchDbnFromDatabase(dbn : String) {
        loading.value = true
        launch {
            val sScores = AppDatabase(getApplication()).satScoreDao().getSatScoreByDbn(dbn)
            satScoreDbnRetrieved(sScores)
            Toast.makeText(getApplication(), "***>>> SATScore ($dbn) has retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun satScoreRetrieved(satScoreList: List<SATScore>) {
        satScores.value = satScoreList
        schoolLoadError.value = false
        loading.value = false
    }

    private fun satScoreDbnRetrieved(satScore: SATScore) {
        singleSatScore.value = satScore
        schoolLoadError.value = false
        loading.value = false
    }

    private fun storeSATScoreLocally(list: List<SATScore>) {
        launch {//access database need to implement a coroutine
            val dao = AppDatabase(getApplication()).satScoreDao()
            dao.deleteAllSAtScore()
            val result = dao.insertAllSATScore(*list.toTypedArray()) //get a list then expands the list into individual element to be store it into db
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toLong()
                ++i
            }
            satScoreRetrieved(list)
        }
    }

    private fun fetchFromRemote() { //Fetch from remote and store it in db
        loading.value = true
        disposable.add(
            schoolsService.getSatScores()
                .subscribeOn(Schedulers.newThread())//on background tread
                .observeOn(AndroidSchedulers.mainThread())//android main thread
                .subscribeWith(object : DisposableSingleObserver<List<SATScore>>(), Disposable {
                    override fun onSuccess(satScoreList: List<SATScore>) {
                        storeSATScoreLocally(satScoreList)
                        //println("***>>> satScores: $satScores")
                        Toast.makeText(getApplication(), "SATScoreList retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        //NotificationsHelper(getApplication()).createNofitication()
                    }

                    override fun onError(e: Throwable) {
                        schoolLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                }))
    }

}