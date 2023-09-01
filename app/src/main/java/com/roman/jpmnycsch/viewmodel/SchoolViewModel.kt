package com.roman.jpmnycsch.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.jpmnycsch.model.School
import com.roman.jpmnycsch.view.SchoolApiService
import com.roman.jpmnycsch.view.SchoolRetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/*
* Class: SchoolViewModel
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27 PM
*/

class SchoolViewModel: ViewModel() {
    private val tag = javaClass.simpleName

    private val schoolApiService = SchoolRetrofitClient.getInstance()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _:CoroutineContext, _:Throwable ->
    onError("Exception: $(throwable.localizedMessage)")
}

    val schools = MutableLiveData<List<School>>()
    val dbn =  MutableLiveData<String?>() //District Borough Number (New York City Department of Education school identifier)
    val schoolLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

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
                    schoolLoadError.value = ""
                    loading.value = false
                } else {
                    onError(" --> Error: ${response.message()}")
                }
            }

        }

    }
    private fun onError(message: String) {
        schoolLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}