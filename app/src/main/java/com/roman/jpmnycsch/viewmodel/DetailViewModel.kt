package com.roman.jpmnycsch.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.roman.jpmnycsch.model.AppDatabase
import com.roman.jpmnycsch.model.SATScore
import kotlinx.coroutines.launch

/*
* Class: DetailViewModel`
* Owner: Roman Edjlali
* Date Created: 09/02/2023 17:27
*/
class DetailViewModel(application: Application) : BaseViewModel(application) {

    private val tag = javaClass.simpleName
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
            satScoreLiveData.value = satScore
        }
    }
}
