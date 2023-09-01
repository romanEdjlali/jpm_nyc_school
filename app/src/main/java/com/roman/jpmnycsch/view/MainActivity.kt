package com.roman.jpmnycsch.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.jpmnycsch.databinding.ActivityMainBinding
import com.roman.jpmnycsch.viewmodel.SchoolViewModel

/*
* Class: MainActivity
* Owner: Roman Edjlali
* Date Created: 08/31/2023 18:59 PM
*/

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    lateinit var viewModel: SchoolViewModel
    private val schoolAdapter = SchoolAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)


        viewModel = ViewModelProviders.of(this).get(SchoolViewModel::class.java)
        viewModel.refresh()

        binding.schoolList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.schools.observe(this, Observer {schools ->
            schools?.let {
                binding.schoolList.visibility = View.VISIBLE
                schoolAdapter.updateSchoolList(it) }
        })

        viewModel.schoolLoadError.observe(this, Observer { isError ->
            binding.listError.visibility = if(isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    binding.listError.visibility = View.GONE
                    binding.schoolList.visibility = View.GONE
                }
            }
        })
    }

    /*fun getSchoolList() {
        var retrofit = SchoolRetrofitClient.getInstance()
        var apiInterface = retrofit.create(SchoolAPIService::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllSchools()
                if (response.isSuccessful()) {
                    //your code for handaling success response


                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }catch (Ex:Exception){
                Log.e("Error",Ex.localizedMessage)
            }
        }

    }*/
}