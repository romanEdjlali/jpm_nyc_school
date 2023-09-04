package com.roman.jpmnycsch.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.roman.jpmnycsch.model.AppDatabase
import com.roman.jpmnycsch.model.SATScore
import kotlinx.coroutines.launch

/*
* Class:  SATScoreViewModel`
* Owner: Roman Edjlali
* Date Created: 09/02/2023 17:27
*/
/*
class SATScoreViewModel(*/
/*private val repository: SATRepository*//*
): ViewModel() {

    private val tag = javaClass.simpleName
    private val apiService = SchoolRetrofitClient.getInstance()


    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler {_, exception ->
        println("Exception: $(throwable.localizedMessage)")
    }

        //store it in Room DB
        val satScoreListData = MutableLiveData<List<SATScore>>()

        val satScoreLiveData = MutableLiveData<SATScore>()
        val satLoadError = MutableLiveData<String?>()
        val satLoading = MutableLiveData<Boolean>()

        private val satService = SchoolRetrofitClient
        //private val disposable = CompositeDisposable()

        fun fetch() {
            val schoolSatScore = SATScore(
                dbn = "DBN",
                school_name = "School Name",
                num_of_sat_test_takers = 7,
                sat_critical_reading_avg_score = 15,
                sat_math_avg_score = 96,
                sat_writing_avg_score = 121
            )
            satScoreLiveData.value = schoolSatScore
            satLoadError.value = ""
            satLoading.value = false
        }


        fun refresh() {
            fetchFromRemote()
        }


        fun fetchFromRemote() {
            satLoading.value = true
            */
/*disposable.add(
               satService.getSatScore().
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposibleSingleObserver<List<SATScore>>() {

                    })
            )*//*

            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response: Response<List<SATScore>> = apiService.getSatScores()
                //withContext(Dispatchers.Main){
                Log.d(tag, "***>>> response is : $response")
                    if(response.isSuccessful) {
                        satScoreListData.value = response.body()
                        Log.d(tag, "***>>> satScoreListData: $satScoreListData")
                        satLoadError.value = ""
                        satLoading.value = false
                    } else {
                        println(" --> Error: ${response.message()}")
                    }
                //}

            }
        }

        override fun onCleared() {
            super.onCleared()
           // disposable.clear()
        }

        //val allSATScores: LiveData<List<SATScore>> = repository.allSATScores

        */
/*    init {
                viewModelScope.launch {
                    repository.refreshSATScores()
                }
            }*//*

    }*/


class DetailViewModel(application: Application) : BaseViewModel(application) {

    val satScoreLiveData = MutableLiveData<SATScore>()

  /*  fun fetch(uuid: Int) {
        launch {
            val satScore = AppDatabase(getApplication()).satScoreDao().getSatScore(uuid)
            satScoreLiveData.value = satScore
        }
    }*/

    fun fetchByDbn(dbn: String) {
        launch {
            val satScore = AppDatabase(getApplication()).satScoreDao().getSatScoreByDbn(dbn)
            Toast.makeText(getApplication(), "satScore is $satScore", Toast.LENGTH_SHORT).show()
            satScoreLiveData.value = satScore
        }
    }
}
