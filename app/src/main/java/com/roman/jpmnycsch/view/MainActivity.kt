package com.roman.jpmnycsch.view

import android.os.Bundle
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import androidx.appcompat.app.AppCompatActivity
import com.roman.jpmnycsch.databinding.ActivityMainBinding
import com.roman.jpmnycsch.viewmodel.ListViewModel

/*
* Class: MainActivity
* Owner: Roman Edjlali
* Date Created: 08/31/2023 18:59
*/

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName

    //private lateinit var navController: NavController
    private  lateinit var binding: ActivityMainBinding

    lateinit var viewModel: ListViewModel
    private val schoolAdapter = SchoolAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        //navController = Navigation.findNavController(this@MainActivity, R.id.fragment)
        //NavigationUI.setupActionBarWithNavController(this@MainActivity,navController)
        setContentView(view)
        window.setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE)

       /* viewModel = ViewModelProviders.of(this).get(SchoolViewModel::class.java)
        viewModel.refresh()

        binding.schoolList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolAdapter
        }

        observeViewModel()*/
    }

    /*fun observeViewModel() {
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
    }*/
    /* override fun onSupportNavigateUp(): Boolean {
         return NavigationUI.navigateUp(navController, null)
     }*/
}